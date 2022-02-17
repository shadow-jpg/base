<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>
<@l.logout />
    <span><a href="/user">User list</a>
</div>
        <form method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="text" placeholder="Message"/>
            <input type="text" name="tag" placeholder="tag" />
            <button type="submit"> add </button>
        </form>
    </div>
<div>Список сообщений</div>
        <form method="get" action="/main">
            <input type="text" name="filter" value="${filter?ifExists}">
            <button type="submit">find</button>
        </form>
    <div>
        <#list messages as message>
    <div>
        <b>${message.id}</b>
        <strong>${message.authorName}</strong>
        <span>${message.text}</span>
        <i>${message.tag}</i>
    </div>
            <#else>
            No data
        </#list>
    </div>

</@c.page>