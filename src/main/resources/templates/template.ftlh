De schakeling
[display_gsc]
[accordion]
<#list items.entrySet() as decade>
[toggle title="${decade.key?c}" state="closed"]
	<div id="content">
		<ul class="nav nav-tabs">
		<#list decade.value.entrySet() as year>
			<li <#if year?is_first>class="active"</#if>><a href="#C${year.key?c}" data-toggle="tab">${year.key?c}</a></li>
		</#list>
		</ul>
	</div>
	<div class="tab-content">
	<#list decade.value.entrySet() as year>
		<div id="C${year.key?c}" class="tab-pane <#if year?is_first> active</#if>">
		<#list year.value.entrySet() as month>
			<#list month.value as edition>
			<div class="span1 archive-edition">
				<a href="/archiefmap/deschakeling/${edition.fileURL}"><div class="archive-month-name deschakeling">${month.key}</div><img class="archive-thumb" src="/archiefmap/deschakeling/${edition.thumpURL}"/><div class="archive-edition-number">${edition.number}</div></a>
			</div>
			</#list>
		</#list>
		</div>
	</#list>
	</div>
[/toggle]
</#list>
[/accordion]
