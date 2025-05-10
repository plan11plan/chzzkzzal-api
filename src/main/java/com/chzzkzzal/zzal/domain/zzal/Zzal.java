package com.chzzkzzal.zzal.domain.zzal;

import com.chzzkzzal.common.domain.BaseTimeEntity;
import com.chzzkzzal.member.domain.Member;
import com.chzzkzzal.zzal.domain.marker.Bookmarkable;
import com.chzzkzzal.zzal.domain.marker.Uploadable;
import com.chzzkzzal.zzal.domain.marker.Viewable;
import com.chzzkzzal.zzal.domain.metadata.MediaMeta;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
public abstract class Zzal extends BaseTimeEntity implements Uploadable, Bookmarkable, Viewable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	protected String channelId;

	@ValidTitle
	protected String title;

	abstract public String getUrl();

	abstract public MediaMeta getMetaInfo();

	abstract public Member getMember();

}
