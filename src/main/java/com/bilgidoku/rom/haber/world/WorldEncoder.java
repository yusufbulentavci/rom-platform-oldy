package com.bilgidoku.rom.haber.world;

import com.bilgidoku.rom.ilk.json.JSONObject;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class WorldEncoder extends MessageToByteEncoder<JSONObject> {

	@Override
	protected void encode(ChannelHandlerContext ctx, JSONObject msg, ByteBuf out)
			throws Exception {
		byte[] s = msg.toString().getBytes("UTF-8");
		out.writeByte((byte) 'F'); // magic number
		out.writeInt(s.length); // data length
		out.writeBytes(s);
	}
}
