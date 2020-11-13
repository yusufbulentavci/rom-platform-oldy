package com.bilgidoku.rom.pg.sqlunit;

/*
 * Slightly modified version of the com.ibatis.common.jdbc.ScriptRunner class
 * from the iBATIS Apache project. Only removed dependency on Resource class
 * and a constructor
 */
/*
 *  Copyright 2004 Clinton Begin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.bilgidoku.rom.pg.sqlunit.model.Clips;
import com.bilgidoku.rom.pg.sqlunit.model.Data;
import com.bilgidoku.rom.pg.sqlunit.model.Run;
import com.bilgidoku.rom.pg.sqlunit.parsing.ClipsParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.CompParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.DataParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.EnumParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.JavaParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.JsParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.MethodParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.RunParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.SchemaParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.StandartParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.TableParser;
import com.bilgidoku.rom.pg.sqlunit.parsing.TypeParser;
import com.bilgidoku.rom.shared.Cookie;
import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.pg.dict.CookieFinder;
import com.bilgidoku.rom.pg.sembol.SembolGorevlisi;

/**
 * Tool to run database scripts
 */
public class SqlUnitParser {

	private static final String KEYWORD_PREFIX = "--@";
	private static final String KEYWORD_END = "--@END";

	private static final String SQL_STATEMENT_DELIMITER = ";";
	private static final String KEYWORD_DEPENDS = "--@DEPENDS:";
	private static final String KEYWORD_UPGRADE = "--@UPGRADE:";

	private static final String[][] STANDART_PARSERS = { { "--@AGGREGATE", "aggregate" }, { "--@DOMAIN", "domain" },
			{ "--@GROUP", "group" }, { "--@LANGUAGE", "language", "trusted,procedural" }, { "--@OPERATOR", "operator" },
			{ "--@ROLE", "role" }, { "--@RULE", "rule" }, { "--@SEQUENCE", "sequence" },
			{ "--@TABLESPACE", "tablespace" }, { "--@TRIGGER", "trigger" }, { "--@INDEX", "index" },
			{ "--@USER", "user" } };

	private String lineSeperator = "\n";
	private List<CompParser> parsers = new ArrayList<CompParser>();

	private Macros macros = new Macros();

	private final RomDb romDb = new RomDb();
	private boolean byPassDb = false;
	private boolean inMacro = false;
	private CookieFinder cf = new CookieFinder() {

		@Override
		public String getCookieByName(Cookie cookie, String cookieName) {
			return null;
		}
	};

	public SqlUnitParser() throws KnownError {
		parsers.add(new MethodParser(cf, romDb));
		parsers.add(new SchemaParser(romDb));
		parsers.add(new TableParser(romDb));
		parsers.add(new TypeParser(romDb));
		parsers.add(new EnumParser(romDb));
		parsers.add(new DataParser(romDb));
		parsers.add(new JavaParser(romDb));
		parsers.add(new RunParser(romDb));
		parsers.add(new ClipsParser(romDb));
		parsers.add(new JsParser(romDb));
		for (String[] sp : STANDART_PARSERS) {
			String[] options = null;
			if (sp.length > 2) {
				options = sp[2].split(",");
			}
			parsers.add(new StandartParser(romDb, sp[0], sp[1], options, false));
		}

	}

	public List<String> parseDependencies(String fileName, String currentPackage) throws IOException, SuException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		InputStream a = classLoader.getResourceAsStream(fileName);
		List<String> depends = new LinkedList<String>();
		if (a == null) {
			throw new SuException(fileName, 0,
					"SQL not found:" + fileName + "\nUri:" + classLoader.getResource(fileName));
		}
		final InputStreamReader reader = new InputStreamReader(a);

		try {

			final LineNumberReader lineReader = new LineNumberReader(reader);
			String line = null;

			while ((line = lineReader.readLine()) != null) {
				String trimmedLine = line.trim();

				if (trimmedLine.length() < 1) {
					// Do nothing
				} else if (trimmedLine.startsWith(KEYWORD_DEPENDS)) {
					final String dependsStr = trimmedLine.substring(KEYWORD_DEPENDS.length());
					final String[] dependsStrs = dependsStr.split(",");
					for (final String string : dependsStrs) {
						String dstr = string.trim();
						if (dstr.length() == 0)
							continue;
						if (dstr.startsWith(".")) {
							if (currentPackage == null) {
								dstr = dstr.substring(1);
							} else {
								dstr = currentPackage + dstr;
							}
						}

						depends.add(dstr);
					}
					continue;
				}
			}

			return depends;

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {

				}
			}
		}

	}

	public SqlUnit go(String fileName, String currentPackage) throws IOException, SuException, NumberFormatException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		InputStream a = classLoader.getResourceAsStream(fileName);

		if (a == null) {
			throw new SuException(fileName, 0,
					"SQL not found:" + fileName + "\nUri:" + classLoader.getResource(fileName));
		}
		final InputStreamReader reader = new InputStreamReader(a);

		try {

			StringBuilder command = null;
			final LineNumberReader lineReader = new LineNumberReader(reader);
			String line = null;
			boolean multiDelimeter = false;
			boolean gotEnd = false;
			SqlUnit sqlUnit = new SqlUnit(fileName, currentPackage);
			SuComp parseComp = null;
			int lineNo = 0;

			while ((line = readLine(lineReader)) != null) {
				++lineNo;
				String trimmedLine = line.trim();
				if (command == null) {
					command = new StringBuilder();
				}

				if (trimmedLine.length() < 1) {
					// Do nothing
				} else if (trimmedLine.startsWith(KEYWORD_PREFIX)) {
					if (multiDelimeter && trimmedLine.startsWith(KEYWORD_END)) {
						multiDelimeter = false;
						gotEnd = true;
					} else if (trimmedLine.startsWith(KEYWORD_DEPENDS)) {
						final String dependsStr = trimmedLine.substring(KEYWORD_DEPENDS.length());
						final String[] dependsStrs = dependsStr.split(",");
						for (final String string : dependsStrs) {
							String dstr = string.trim();
							if (dstr.length() == 0)
								continue;
							if (dstr.startsWith(".")) {
								if (currentPackage == null) {
									dstr = dstr.substring(1);
								} else {
									dstr = currentPackage + dstr;
								}
							}

							sqlUnit.addDependency(dstr);
						}
					} else if (trimmedLine.startsWith(KEYWORD_UPGRADE)) {
						if (parseComp == null) {
							throw new SuException(fileName, lineNo, "Upgrade string without component;" + trimmedLine);
						}
						if (trimmedLine.length() < KEYWORD_UPGRADE.length() + 3) {
							throw new SuException(fileName, lineNo, "Upgrade string is too short:->" + trimmedLine);
						}
						final String upgradeStr = trimmedLine.substring(KEYWORD_UPGRADE.length() + 1);
						int pos = upgradeStr.indexOf(' ');
						if (pos <= 0) {
							throw new SuException(fileName, lineNo,
									"Upgrade string should have version number:->" + trimmedLine);
						}
						int version = Integer.parseInt(upgradeStr.substring(0, pos));
						String upCmd = upgradeStr.substring(pos);
						sqlUnit.addUpgrade(parseComp, version, upCmd, lineNo);
						continue;
					} else {

						if (trimmedLine.startsWith("--@MACRO")) {
							multiDelimeter = true;
							newMacro(fileName, lineReader, lineNo, trimmedLine);
						} else
							for (CompParser parser : this.parsers) {
								if (!trimmedLine.startsWith(parser.getKeyword())) {
									continue;
								}
								if (parseComp != null) {
									throw new SuException(fileName, lineNo,
											"Can not be nested top component:" + " line " + trimmedLine);
								}
								if (command.toString().length() != 0) {
									throw new SuException(fileName, lineNo, "Statement not ended before component:->"
											+ command.toString() + "<-line " + trimmedLine);
								}
								multiDelimeter = true;
								String nextLine = seeNextLine(lineReader);
								if (nextLine == null) {
									throw new SuException(fileName, lineNo,
											"Definition missing for " + parser.getKeyword() + " Line:" + trimmedLine);
								}
								parseComp = parser.parse(sqlUnit, trimmedLine, nextLine, lineNo);
//								syso(trimmedLine + nextLine);
								sqlUnit.addComp(parseComp);
								if (!byPassDb)
									romDb.addComp(parseComp);
								break;
							}
					}

				} else if (trimmedLine.startsWith("--")) {
					continue;
				}

				if (gotEnd || (!multiDelimeter && trimmedLine.endsWith(SQL_STATEMENT_DELIMITER))) {
					if (!gotEnd) {
						if (trimmedLine.startsWith("--")) {
							continue;
						}
						addToCommand(command, line);
					}
					if (this.inMacro) {
						this.inMacro = false;
					} else {
						if (parseComp == null) {
							throw new RuntimeException(
									"Component is null;lineNo:" + lineNo + "for:" + line + " nextLine:");
						}
						sqlUnit.addCommand(parseComp, command.toString(), lineNo);
					}
					if (gotEnd) {
						parseComp = null;
						gotEnd = false;
					}
					command = null;
				} else if (trimmedLine.startsWith("--")) {
					continue;
				} else {
					if (parseComp != null && parseComp.isLineBased()) {
						line = line.trim();
						if (line.length() != 0) {
							StringBuilder sb = new StringBuilder(line);
							sqlUnit.addCommand(parseComp, sb.toString(), lineNo);
						}
					} else {
						addToCommand(command, line);
					}
				}
			}

			return sqlUnit;

		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {

				}
			}
		}

	}

	private void addToCommand(StringBuilder command, String line) {
		line = line.trim();
		if (line.length() == 0)
			return;
		command.append(line);
		command.append(lineSeperator);
	}

	private void newMacro(String fileName, final LineNumberReader lineReader, int lineNo, String trimmedLine)
			throws IOException, SuException {
		this.inMacro = true;
		String nextLine = seeNextLine(lineReader);
		if (nextLine == null) {
			throw new SuException(fileName, lineNo, "Definition missing for macro Line:" + trimmedLine);
		}
		int ind = trimmedLine.indexOf(" ");
		if (ind < 0) {
			throw new SuException(fileName, lineNo, "Syntax error for macro title:" + trimmedLine);
		}

		String macroTitle = trimmedLine.substring(ind).trim();
		if (macroTitle.length() == 0) {
			throw new SuException(fileName, lineNo, "Syntax error for macro title:" + trimmedLine);
		}
		ind = macroTitle.indexOf(' ');
		if (ind < 0) {
			this.macros.addMacro(macroTitle, new SuMacro(nextLine));
			return;
		}
		String macroName = macroTitle.substring(0, ind).trim();
		String left = macroTitle.substring(ind).trim();

		this.macros.addMacro(macroName, new SuMacro(nextLine));
	}

	private String readLine(LineNumberReader lineReader) throws IOException {
		String l = lineReader.readLine();
		try {
			l = SembolGorevlisi.tek().processLine(l);
		} catch (KnownError e) {
			throw new RuntimeException(e);
		}
		if (l != null && l.trim().endsWith("{dispatchresource")) {
			StringBuilder sb = new StringBuilder();
			sb.append(l);
			sb.append("\n");

			while (!l.trim().endsWith("}!<")) {
				l = lineReader.readLine();
				sb.append(l);
				sb.append("\n");
			}
			return macros.replaceMacro(sb.toString());
		}

		return macros.replaceMacro(l);

	}

	private String seeNextLine(LineNumberReader lineReader) throws IOException {
		lineReader.mark(5000);
		StringBuilder sb = new StringBuilder();
		do {
			String nextString = lineReader.readLine();
			if (nextString == null) {
				return null;
			}
			if (nextString.startsWith(KEYWORD_UPGRADE) || nextString.indexOf(KEYWORD_END) >= 0)
				break;
			sb.append(nextString);
		} while (true);
		lineReader.reset();
		return macros.replaceMacro(sb.toString());
	}

	public void forTest() {
		lineSeperator = " ";
//		setByPassDb(true);
	}

	public RomDb getRomDb() {
		return romDb;
	}

	public boolean isByPassDb() {
		return byPassDb;
	}

	public void setByPassDb(boolean byPassDb) {
		this.byPassDb = byPassDb;
	}
}
