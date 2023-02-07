<span class="page-title aligncenter">Archief de Schakeling</span>
[accordion]
<#assign dec = 0>
<#assign year = 0>
<#assign decYear = 0>
<#list list as item>	
	<#-- new decad -->
	<#if dec != item.decade>
		<#assign dec = item.decade>
			[toggle title="${decYear?c}" state="closed"]
				<div id="content">
					<ul class="nav nav-tabs">
		<#-- Loop over years of decade -->
		<#list list as decItem>
			<#if dec == decItem.decade && decYear != decItem.year>
				<#assign decYear = decItem.year>				
						<li class=""><a href="#C${decYear?c}" data-toggle="tab">${decYear?c}</a></li>
			</#if>
		</#list>
					</ul>
				</div>
			<div class="tab-content">
	</#if>
	<#if year != item.year>
				<div id="C${item.year?c}" class="tab-pane">
	</#if>
					<div class="span1 archive-edition">
						<a href="/deschakeling/${item.fileURL}"><div class="archive-month-name">${item.title}</div><img class="archive-thumb" src="/deschakeling/${item.thumpURL}"/><div class="archive-edition-number">${item.number?c}</div></a>
					</div>
	<#if year != item.year>
		<#assign year = item.year>
				</div>
	</#if>
</#list>
[/accordion]
