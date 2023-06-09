package com.sunmi.trans;

import android.os.Parcel;
import android.os.Parcelable;

public class TransBean implements Parcelable {

	private final byte type;
	private final String text;
	private byte[] data = null;
	private final int dataLength;
	
	/*public TransBean(){
		type = 0;
		data = null;
		text = "";
		dataLength = 0;
	};
	
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		if(data != null){
			dataLength = data.length;
			this.data = new byte[dataLength];
			System.arraycopy(data, 0, this.data, 0, dataLength);
		}
	}*/

	public TransBean(Parcel source){
		this.type = source.readByte();
		this.dataLength = source.readInt();
		this.text = source.readString();
		if(dataLength > 0){
			this.data = new byte[dataLength];
			source.readByteArray(data);
		}
	}
	
	/*public TransBean(byte type, String text, byte[] data){
		this.type = type;
		this.text = text;
		if(data != null){
			this.dataLength = data.length;
			this.data = new byte[dataLength];
			System.arraycopy(data, 0, this.data, 0, dataLength);
		}
	}*/
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte(type);
		dest.writeInt(dataLength);
		dest.writeString(text);
		if(data != null){
			dest.writeByteArray(data);
		}
	}
	
	public static Parcelable.Creator<TransBean> CREATOR = new Parcelable.Creator<TransBean>(){

		@Override
		public TransBean createFromParcel(Parcel source) {
			return new TransBean(source);
		}

		@Override
		public TransBean[] newArray(int size) {
			return new TransBean[size];
		}		
	};

}
