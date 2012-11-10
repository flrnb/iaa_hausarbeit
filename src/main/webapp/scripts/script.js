/**
 * Wechsle von collapsed => expanded und expanded => collapsed für den zum
 * Button gehörenden Content-Bereich
 * 
 * @param button
 * @return void
 * @author Christopher Biel
 */
function togglePanel(button) {
	var table_content = $(button).parents('table').find('td.c-content')
	table_content.toggleClass(
			'collapsed expanded');
	if (table_content.hasClass('expanded'))
		$(button).html('Einklappen')
	else
		$(button).html('Ausklappen')
}

/**
 * setzt den Status des Eintrags mit dem übergebenen index auf gelöscht oder nicht gelöscht
 * 
 * @param index
 * @author Florian Borchert
 */
function toggleDeleted(index) {
	var td_id = "#editable_" + index
	var hiddenfield_id = "#delete_" + index
	if( $(td_id).hasClass("deleted")) {
		$(td_id + " > input").removeAttr("disabled")
		$(hiddenfield_id).val(false);
	} else {
		$(td_id + " > input").attr("disabled", "disabled")
		$(hiddenfield_id).val(true);
	}
	$(td_id).toggleClass("deleted");
}