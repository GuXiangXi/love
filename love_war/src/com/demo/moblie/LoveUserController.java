package com.demo.moblie;

import java.util.List;

import net.sf.json.JSONObject;

import com.jfinal.core.Controller;

/**
 * BlogController
 * 所有 sql 与业务逻辑写在 Model 或 Service 中，不要写在 Controller 中，养成好习惯，有利于大型项目的开发与维护
 */

public class LoveUserController extends Controller {
	public void index() {
		setAttr("blogPage", LoveUser.me.paginate(getParaToInt(0, 1), 10));
		render("blog.html");
	}
	
	public void add() {
		String str = getPara("param");
		JSONObject json= JSONObject.fromObject( str ); 
		int accountBox = Integer.parseInt((String) json.get("accountBox"));
		String username = (String) json.get("username");
		String passwordBox = (String) json.get("passwordBox");
		
		boolean flag = false;
		
		LoveUser luTest = new LoveUser();
		List<LoveUser> luList = luTest.find("select * from loveuser where code = "+accountBox+" ");
		if(luList != null && luList.size() > 0){
			
		}else{
			LoveUser lu = new LoveUser();
			lu.set("code", accountBox);
			lu.set("name", username);
			lu.set("password", passwordBox);
			lu.set("role", "0");
			flag = lu.save();
		}
		
		this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		
		if(flag){
			renderText("true");
		}else{
			renderText("false");
		}
		
		
	}
	
	
	public void login() {
		String str = getPara("param");
		JSONObject json= JSONObject.fromObject( str ); 
		int account = Integer.parseInt((String) json.get("account"));
		String password = (String) json.get("password");
		
		boolean flag = false;
		
		LoveUser luTest = new LoveUser();
		List<LoveUser> luList = luTest.find("select * from loveuser where code = "+account+" and password = '"+password+"' ");
		if(luList != null && luList.size() > 0){
			flag = true;
		}
		
		this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		
		if(flag){
			renderText("true");
		}else{
			renderText("false");
		}
		
		
	}
	
	public void title() {
		
		String str = getPara("param");
		JSONObject json= JSONObject.fromObject( str ); 
		String accountstr =(String) json.get("account");
		
		this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		
		if( !"account".equals(accountstr)){
			LoveTitle luTest = new LoveTitle();
			List<LoveTitle> ltList = luTest.find("select * from LoveTitle where isshow = '"+0+"' and id = "+accountstr+"");
			
			this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
			
			if(ltList != null && ltList.size() > 0){
				renderJson(ltList);
			}else{
				renderText("false");
			}
		}else{
			LoveTitle luTest = new LoveTitle();
			List<LoveTitle> ltList = luTest.find("select * from LoveTitle where isshow = '"+0+"'");
			
			
			
			if(ltList != null && ltList.size() > 0){
				renderJson(ltList);
			}else{
				renderText("false");
			}
		}
		
		
		
		
	}
	
	public void setJob() {
		
		String str = getPara("param");
		JSONObject json= JSONObject.fromObject( str ); 
		String accountstr =(String) json.get("account");
		String value =(String) json.get("value");
		String titleId =(String) json.get("titleId");
		
		
		LoveJob lj = new LoveJob();
		
		
		LoveUser luTest = new LoveUser();
		luTest = luTest.findFirst("select * from LoveUser where code = "+accountstr+"");
		
		
		List<LoveJob> listlj = lj.find("select * from lovejob where titleId ="+titleId+"  and loveId = "+accountstr+"");
		
		if(listlj != null && listlj.size() > 0){
			for (LoveJob loveJob : listlj) {
				lj.deleteById(loveJob.get("id"));
			}
			
			lj.set("titleId", titleId);
			lj.set("loveId", accountstr);
			lj.set("love", value);
			lj.set("loveName", luTest.get("name"));
			lj.save();
			
		}else{
			lj.set("titleId", titleId);
			lj.set("loveId", accountstr);
			lj.set("love", value);
			lj.set("loveName", luTest.get("name"));
			lj.save();
		}
		
		List<LoveJob> listljlast = lj.find("select * from lovejob where titleId ="+titleId+"");
		
		this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		renderJson(listljlast);
		
		
		
		
		
	}
	
	public void getJob() {
		
		String str = getPara("param");
		JSONObject json= JSONObject.fromObject( str ); 
		String accountstr =(String) json.get("account");
		String titleId =(String) json.get("titleId");
		
		
		LoveJob lj = new LoveJob();
		
		
		
		this.getResponse().setHeader("Access-Control-Allow-Origin", "*");
		List<LoveJob> listljlast = lj.find("select * from lovejob where titleId ="+titleId+"");
		
		renderJson(listljlast);
	}
	
	
	public void save() {
		getModel(LoveUser.class).save();
		redirect("/blog");
	}
	
	public void edit() {
		setAttr("blog", LoveUser.me.findById(getParaToInt()));
	}

	public void update() {
		getModel(LoveUser.class).update();
		redirect("/blog");
	}
	
	public void delete() {
		LoveUser.me.deleteById(getParaToInt());
		redirect("/blog");
	}
}


