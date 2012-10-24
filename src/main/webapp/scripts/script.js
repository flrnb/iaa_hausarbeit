/**
 * Wechsle von collapsed => expanded und expanded => collapsed für den zum
 * Button gehörenden Content-Bereich
 * 
 * @param button
 * @return void
 */
function togglePanel(button) {
	$(button).parents('table').find('td.c-content').toggleClass(
			'collapsed expanded');
}