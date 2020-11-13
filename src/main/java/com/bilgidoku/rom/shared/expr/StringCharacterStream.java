package com.bilgidoku.rom.shared.expr;

import java.io.IOException;

import com.bilgidoku.rom.shared.expr.CharStream;

public class StringCharacterStream  implements CharStream{
	public final String string;
	public int position = 0;
	public int mark=0;

	public StringCharacterStream(String string) {this.string = string;}
	public char readChar() throws java.io.IOException {
		if (position==string.length()) throw new IOException("End of line");
		return string.charAt(position++); 
	}
	public int getColumn() { return position; }
	public int getLine() { return 0; }
	public int getEndColumn() { return position; }
	public int getEndLine() { return 0; }
	public int getBeginColumn() { return mark; }
	public int getBeginLine() { return 0; }
	public void backup(int amount) { position-=amount; }
	public char BeginToken() throws java.io.IOException { mark=position; return readChar(); }
	public String GetImage() { return string.substring(mark,position); }
	public char[] GetSuffix(int len) { return string.substring(position - len,position).toCharArray(); }
	public void Done() {}
  }