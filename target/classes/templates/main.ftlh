<#import "parts/common.ftlh" as c>
<#import "parts/login.ftlh" as l>
<@c.page>
<div>
<@l.logout />
    <a href="/chat">chat</a>
    <span><a href="/user">User list</a>
</div>
        <form method="post" enctype="multipart/form-data">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
            <input type="text" name="text" placeholder="Message"/>
            <input type="text" name="tag" placeholder="tag" />
            <input type="file" name="file">
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
        <div>
            <#if message.filename??>
                <img src="/img/${message.filename}">
            </#if>
        </div>
    </div>
            <#else>
            No data
        </#list>
    </div>

</@c.page>