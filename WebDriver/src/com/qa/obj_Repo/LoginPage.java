/**
 * 
 */
package com.qa.obj_Repo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * @author hari
 *
 */
public class LoginPage 
{

	WebDriver driver;
	
	
	public  LoginPage(WebDriver ldriver)
	{
	this.driver=ldriver;
	}
	
	@FindBy(how=How.ID,using="user_login")
	@CacheLookup
	WebElement username;
	
	@FindBy(how=How.ID,using="user_pass")
	@CacheLookup
	WebElement password;
	
	@FindBy(how=How.ID,using="wp-submit")
	@CacheLookup
	WebElement submit_btn;

	public void login(String uid , String pwd){
		
		username.sendKeys(uid);
		password.sendKeys(pwd);
		submit_btn.click();
	}
	
}
