<#-- @ftlvariable name="results" type="com.wutiarn.flibustabot.model.opds.BookSearchResult" -->
<b>Результаты поиска по запросу ${results.query}</b>

<#if results.books??>
<#list results.books as book>
<b>${book.author.name}</b>
${book.title}
EPUB: /dl_epub_${book.id} FB2: /dl_fb2_${book.id} MOBI: /dl_mobi_${book.id}

</#list>
<#else>
Результатов не найдено
</#if>