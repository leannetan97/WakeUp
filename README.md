# WakeUp
This is the assignment for WIX3004 Mobile Application Development.

## About
This is an Android Mobile Application which aim to wake user up when according to the alarm set. The users are able to set game that they wish to play in order to close the alarm.

## Important: Some constraints and naming conventions to follow
1. Under res/ directory, if an image is needed to be used, name it as either:
   * `logo/logo_{your_image_name.xxx}`, 
   * `background/background_{your_image_name.xxx}` or in general 
   * `{functional}/{functional}_{your_image_name.xxx}`

2. For any layout/activity/fragment created, follow **PascalCase** convention when naming it at the prompt up window. Naming activity/fragment/layout will go with ActivityDescription followed by Element.
E.g. **MainActivity**, **TextFragment**, **RegisterPageActivity**.

3. For any view/layout id, it will be following the pattern like:
    * `btn_submitNow` - btn for Button view, followed by the description in **_camelCase**.
    * `tv_submitNow` - tv for Text view, followed by the description in **_camelCase**.
    * `tfv_submitNow` - tf for TextField view, followed by the description in **_camelCase**.
    * `iv_submitNow` - iv for Image view, followed by the description in **_camelCase**.
    * ... and many other cases, we'll discuss later if conflict encountered.

4. The **Primary** and **Secondary** color for this project will be:
     * PrimaryColor - `#8e24aa`
     * PrimaryLight - `#c158dc`
     * PrimaryDark - `#5c007a`
     * SecondaryColor - `#ffc107`
     * SecondaryLight - `#fff350`
     * SecondaryDark - `#c79100`

5. Date format using java.util.Date:

``` java
// saving date
private SimpleDateFormat dateFormatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date date = dateFormatter.parse("2019-12-30 23:37:50");

// date to string example
private SimpleDateFormat dateFormatter =new SimpleDateFormat("EEEE, MMM dd hh:mm:ss a");
String dateStr = dateFormatter.format(date);
```
