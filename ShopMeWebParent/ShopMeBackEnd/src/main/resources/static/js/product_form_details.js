function addNextDetailSection() {
    let allDivDetails = $("[id^='divDetail']"); /** first id="divDetail0" defined in product_details.html then id="divDetail1" id="divDetail2" ... */
    let divDetailsCount = allDivDetails.length;

    htmlDetailSection = `
		<div class="form-inline" id="divDetail${divDetailsCount}">
			<label class="m-3">Name:</label>
			<input type="text" class="form-control w-25" name="detailNames" maxlength="255" />
			<label class="m-3">Value:</label>
			<input type="text" class="form-control w-25" name="detailValues" maxlength="255" />
		</div>	
	`;

    $("#divProductDetails").append(htmlDetailSection);

    let previousDivDetailSection = allDivDetails.last(); // get first previous value of the last element -> For instance : there are 2 elements,
    // it only shows the icon at first one, like 3 elements , it shows the icon at 2th element.

    let previousDivDetailID = previousDivDetailSection.attr("id");

    htmlLinkRemove = `
		<a class="btn fas fa-times-circle fa-2x icon-dark"
			href="javascript:removeDetailSectionById('${previousDivDetailID}')"
			title="Remove this detail"></a>
	`;

    previousDivDetailSection.append(htmlLinkRemove);

    $("input[name='detailNames']").last().focus();
}

function removeDetailSectionById(id) {
    $("#" + id).remove();
}