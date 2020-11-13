package com.bilgidoku.rom.pg.dict;

import java.sql.Timestamp;

import com.bilgidoku.rom.izle.Astate;
import com.bilgidoku.rom.izle.MC;

public class RomResource {
	private static MC mc = new MC(RomResource.class);

	private short useCount = 0;
	private final TypeControl romType;
	private final Timestamp modifiedDate;
	private final String htmlFile;
	private final boolean isContainer;
	private final String container;
	private final Timestamp creationDate;

	private String owner;
	private final String uri;
	private String group;
	private String[] relatedCids;
	private Long mask;
	private String delegated;

	// container,html_file,modified_date,creation_date,delegated,ownercid,gid,relatedcids,mask
	private static Astate newResource = mc.c("new-resource");

	public RomResource(TypeControl romType, String uri, String container, String htmlFile, Timestamp modifiedDate, Timestamp creationDate,
			String delegated, String owner, String group, String[] relatedCids, Long mask, boolean isContainer) {
		this.romType = romType;
		this.uri = uri;
		this.container = container;
		this.creationDate = creationDate;
		this.modifiedDate = modifiedDate;
		this.htmlFile = htmlFile;
		this.owner = owner;
		this.isContainer = isContainer;
		this.group = group;
		this.relatedCids = relatedCids;
		this.mask = mask;
		this.delegated = delegated;
		// "New resource of schema:"+romType.getSchema().getName()+" type:"+romType.getTableName()+" isContainer:"+isContainer
		newResource.more();
	}

	public short getUseCount() {
		return useCount;
	}

	public TypeControl getTable() {
		return romType;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public String getHtmlFile() {
		return htmlFile;
	}

	public String getOwner() {
		return owner;
	}

	public boolean isContainer() {
		return isContainer;
	}

	public String getContainer() {
		return container;
	}

	public String getUri() {
		return uri;
	}

	public String getGroup() {
		return group;
	}

	public String[] getRelatedCids() {
		return relatedCids;
	}

	public Long getMask() {
		return mask;
	}

	public String getDelegated() {
		return delegated;
	}

	public boolean mayNeedDelegation() {
		return owner == null || group == null || relatedCids == null || mask == null;
	}

	public void delegate(RomResource rr) {
		if(owner==null && rr.owner!=null)
			owner=rr.owner;
		if(group==null && rr.group!=null)
			group=rr.group;
		if(relatedCids==null && rr.relatedCids!=null)
			relatedCids=rr.relatedCids;
		if(mask==null && rr.mask!=null)
			mask=rr.mask;
	}

}
