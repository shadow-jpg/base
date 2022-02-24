<#import "parts/common.ftlh" as c>

<@c.page>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add new message
    </a>
    <div class="collapse" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="hidden" name="_csrf"  value="${_csrf.token}" />
                </div>

                <div class="form-group">
                    <input type="text" name="text" class="form-control" placeholder="Message"/>
                </div>

                <div class="form-group">
                    <input type="text" name="tag" class="form-control" placeholder="tag" />
                </div>

                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile">
                        <label class="custom-file-label" for="customFile">Pick file</label>
                    </div>
                </div>

                <div class="form-group">
                    <button type="submit" class="btn btn-primary "> send </button>
                </div>
            </form>
        </div>
    </div>

    <div class="form-row" >
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter?ifExists}" placeholder="Search by #">
                <button type="submit" class="btn btn-primary ml-2">find</button>
            </form>
        </div>
    </div>

    <div class="card-columns">
        <#list messages as message>
            <div class="card my-4">
                <#if message.filename??>
                    <img src="/img/${message.filename}" class="card-img-top">
                </#if>
                <div class="m-2">
                    <span>${message.text}</span>
                    <i>${message.tag}</i>
                </div>
                <div class="card-footer text-muted">
                    ${message.authorName}
                </div>
            </div>
        <#else>
            No message
        </#list>
    </div>
</@c.page>