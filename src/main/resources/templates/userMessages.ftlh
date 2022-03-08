<#import "parts/common.ftlh" as c>
<#include "parts/security.ftlh">
<@c.page>
    <#if message??>
        <#if isCurrentUser>
            <#include "parts/postRedactor.ftlh">
        </#if>
    </#if>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/userMessage" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by #">
                <button type="submit" class="btn btn-primary ml-2">find</button>
            </form>
        </div>
    </div>
    <#include "parts/postPage.ftlh">


</@c.page>