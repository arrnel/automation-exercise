package com.automationexercise.tests.util;

import com.microsoft.playwright.Dialog;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;

public class AlertUtil {

    @Step("Accept browser alert")
    public void acceptAlert(Page page) {
        page.onDialog(Dialog::accept);
    }

    @Step("Dismiss browser alert")
    public void dismissAlert(Page page) {
        page.onDialog(Dialog::dismiss);
    }

}
