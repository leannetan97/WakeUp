
# WakeUp
## [Important] Some constraints and naming conventions to follow
Under res/ directory, if an image is needed to be used, name it as either 
<br/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**logo/logo_{your_image_name.xxx}**,
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**background/background_{your_image_name.xxx}**, or in general,
<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**{functional}/{functional}_{your_image_name.xxx}**

For any layout/activity/fragment created, follow **PascalCase** convention when naming it at the prompt up window. Naming activity/fragment/layout will go with ActivityDescription followed by Element. E.g. **MainActivity**, **TextFragment**, **RegisterPageActivity**.

For any view/layout id, it will be following the pattern like:

**btn_submitNow** - btn for Button view, followed by the description in **_camelCase**.

**tv_submitNow** - tv for Text view, followed by the description in **_camelCase**.

**tfv_submitNow** - tf for TextField view, followed by the description in **_camelCase**.

**iv_submitNow** - iv for Image view, followed by the description in **_camelCase**.

... and many other cases, we'll discuss later if conflict encountered.

