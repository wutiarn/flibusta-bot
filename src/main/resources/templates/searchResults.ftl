<#-- @ftlvariable name="results" type="com.wutiarn.flibustabot.model.opds.BookSearchResult" -->
<b>Результаты поиска по запросу ${results.query}</b>

<#list results.books as book>
${book.author.name} ${book.title}
/epub_${book.id} /fb2_${book.id} /mobi_${book.id}
</#list>