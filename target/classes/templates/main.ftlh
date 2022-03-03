<#import "parts/common.ftlh" as c>

<@c.page>

    <#include "parts/postRedactor.ftlh">

    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}"
                       placeholder="Search by #">
                <button type="submit" class="btn btn-primary ml-2">find</button>
            </form>
        </div>
    </div>
    <#include "parts/postPage.ftlh">

</@c.page>