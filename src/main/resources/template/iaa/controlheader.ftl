<#include "/${parameters.templateDir}/iaa/controlheader-core.ftl" />
    <td
<#if hasFieldErrors>
    class="errorsBg" <#t/>
</#if>
<#if parameters.align??>
    align="${parameters.align?html}"<#t/>
</#if>
><#t/>