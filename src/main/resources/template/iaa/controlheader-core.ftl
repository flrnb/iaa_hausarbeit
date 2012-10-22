<#--
	Only show message if errors are available.
	This will be done if ActionSupport is used.
-->
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#--
	if the label position is top,
	then give the label its own row in the table
-->
<tr>
 
<td class="tdLabel <#t/>
<#--
<#if hasFieldErrors>
errorsBg"<#t/>
</#if>
-->
><#rt/>
 
<#if parameters.label??>
    <label <#t/>
<#if parameters.id??>
        for="${parameters.id?html}" <#t/>
</#if>
<#if hasFieldErrors>
        class="errorLabel"<#t/>
<#else>
        class="label"<#t/>
</#if>
 ><#t/>
 
<#if parameters.required?default(false) && parameters.requiredposition?default("right") != 'right'>
    <span class="required">*</span><#t/>
</#if>
${parameters.label?html}<#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
 	<span class="required">*</span><#t/>
</#if>
 
</label><#t/>
</#if>
 
</td><#lt/>