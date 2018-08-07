package com.pall.dotcom.service.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class BulletContainer {
	@Inject @Named("bullet1")
	private Resource bullet1Res;
	@Inject @Named("bullet2")
	private Resource bullet2Res;
	@Inject @Named("bullet3")
	private Resource bullet3Res;
	
	private Bullet bullet1,bullet2,bullet3;
	@PostConstruct
	public void init(){
		
		if(bullet1Res!=null){
			 bullet1 = bullet1Res.adaptTo(Bullet.class);
		}
		if(bullet2Res!=null){
			 bullet2 = bullet2Res.adaptTo(Bullet.class);
		}
		if(bullet3Res!=null){
			 bullet3 = bullet3Res.adaptTo(Bullet.class);
		}
	}
	public Bullet getBullet1() {
		return bullet1;
	}
	public Bullet getBullet2() {
		return bullet2;
	}
	public Bullet getBullet3() {
		return bullet3;
	}
}
