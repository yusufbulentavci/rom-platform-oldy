package com.bilgidoku.rom.pg.dict.mapper;

import io.netty.handler.codec.http.multipart.FileUpload;

import java.util.List;

import com.bilgidoku.rom.shared.err.KnownError;
import com.bilgidoku.rom.shared.err.NotInlineMethodException;
import com.bilgidoku.rom.shared.err.ParameterError;
import com.bilgidoku.rom.ilk.json.JSONException;
import com.bilgidoku.rom.pg.veritabani.DbThree;
import com.bilgidoku.rom.pg.dbfs.DbfsGorevlisi;
import com.bilgidoku.rom.pg.dict.ArgMapper;
import com.bilgidoku.rom.pg.dict.CallInteraction;
import com.bilgidoku.rom.pg.dict.types.TypeHolder;

public class FileMapper extends ArgMapper {

	private String paramName;

	public FileMapper(short i, String varName, TypeHolder sqlTypeHolder, boolean canBeNull) {
		super(i, canBeNull);
		this.paramName = varName;
		this.sqlType = sqlTypeHolder;
	}

	public String toString() {
		return "FileMapper:" + " name:" + this.paramName;
	}

	@Override
	public void setValue(CallInteraction request, String self, DbThree ps) throws KnownError, JSONException,
			NotInlineMethodException, KnownError, ParameterError {
		if (sqlType.isArray()) {
			List<FileUpload> list = request.getFilesParam(this.paramName, false);
			String[] items = new String[list.size()];
			int i = 0;
			for (FileUpload item : list) {
				String fn = DbfsGorevlisi.tek().make(request.getHostId(), item);
				items[i++] = fn;
			}

			ps.setArray("text", items);
			return;
		}

		FileUpload item = request.getFileParam(this.paramName, true);
		String fn=DbfsGorevlisi.tek().make(request.getHostId(), item);
		ps.setString(fn);
	}

	@Override
	public Object getValue(CallInteraction request, String self) throws KnownError, ParameterError {
		try {
			if (sqlType.isArray()) {
				List<FileUpload> list = request.getFilesParam(this.paramName, false);
				String[] items = new String[list.size()];
				int i = 0;
				for (FileUpload item : list) {
					String fn = DbfsGorevlisi.tek().make(request.getHostId(), item);
					items[i++] = fn;
				}

				return items;
			}

			return getFileItem(request);
		} catch (NotInlineMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private Object getFileItem(CallInteraction request) throws NotInlineMethodException, KnownError, ParameterError {
		FileUpload item = request.getFileParam(this.paramName, true);
		return DbfsGorevlisi.tek().make(request.getHostId(), item);
	}
	
}
