package com.calclab.hablar.selenium.userpage;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.calclab.hablar.selenium.HablarSeleniumTest;
import com.calclab.hablar.selenium.vcard.VCardPageObject;

public class UserPageTests extends HablarSeleniumTest {

    @Test
    public void shoulChangeStatus() {
	login();
	userPage.getHeader().click();
	userPage.getStatus().sendKeys("Sleeping");
	userPage.getClose().click();
	webtester.isTextPresent("Sleeping");
	logout();
    }

    @Test
    public void shouldSavePrefs() {
	login();
	userPage.getHeader().click();
	userPage.waitFor(userPage.getTitleSignals());
	togglePrefs();
	userPage.getClose().click();
	logout();
	login();
	userPage.getHeader().click();
	userPage.waitFor(userPage.getTitleSignals());
	togglePrefs();
	Assert.assertEquals("on", userPage.getTitleSignals().getAttribute("value"));
	Assert.assertEquals("on", userPage.getIncomingNotifications().getAttribute("value"));
	Assert.assertEquals("on", userPage.getRosterNotifications().getAttribute("value"));
	userPage.getClose().click();
	logout();
    }

    @Test
    public void shouldSaveVCard() {
	login();
	final String value = getTempString();
	userPage.getHeader().click();
	userPage.getName().clear();
	userPage.getFamilyName().clear();
	userPage.getGivenName().clear();
	userPage.getMiddleName().clear();
	userPage.getNickName().clear();
	userPage.getHomepage().clear();
	userPage.getEmail().clear();
	userPage.getOrganizationName().clear();
	userPage.getName().sendKeys(value);
	userPage.getFamilyName().sendKeys(value);
	userPage.getGivenName().sendKeys(value);
	userPage.getMiddleName().sendKeys(value);
	userPage.getNickName().sendKeys(value);
	userPage.getHomepage().sendKeys(value);
	userPage.getEmail().sendKeys(value);
	userPage.getOrganizationName().sendKeys(value);
	userPage.getClose().click();
	logout();
	login();
	userPage.getHeader().click();
	checkVCard(value, userPage);
	userPage.getClose().click();
	addSeleniumBuddy();
	roster.getHeader().click();
	roster.getItemMenu("", "selenium@localhost").click();
	roster.getSeeBuddyVCardAction().click();
	sleep(1000);
	checkVCard(value, otherVCardPage);
	removeSeleniumBuddy();
	logout();
    }

    private void checkVCard(final String value, final VCardPageObject page) {
	Assert.assertEquals(page.getName().getAttribute("value"), value);
	Assert.assertEquals(page.getFamilyName().getAttribute("value"), value);
	Assert.assertEquals(page.getGivenName().getAttribute("value"), value);
	Assert.assertEquals(page.getNickName().getAttribute("value"), value);
	Assert.assertEquals(page.getMiddleName().getAttribute("value"), value);
	Assert.assertEquals(page.getHomepage().getAttribute("value"), value);
	Assert.assertEquals(page.getEmail().getAttribute("value"), value);
	Assert.assertEquals(page.getOrganizationName().getAttribute("value"), value);
    }

    private void togglePrefs() {
	userPage.getTitleSignals().click();
	userPage.getIncomingNotifications().click();
	userPage.getRosterNotifications().click();
    }
}
