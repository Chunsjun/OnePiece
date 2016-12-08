/*!
 * sortable.js
 *
 * Copyright 2014, Phillip Senn
 * Dual licensed under the MIT or GPL Version 2 licenses.
 */

window.fw0 = {}; // global scope
(function() {
	// head = local scope for the table head
	// col  = local scope for the column
	// row  = local scope for the row
	// tr   = Table Row element
	fw0.sortable = function(index, element) { // This is called from draggable.js
		element.find('th .fw0Unicode').remove();
		processHead(0,element);
	}
	$('body table').not('.no-sort').find('thead').each(processHead);
	function processHead(index, element) {  // body thead because of ColdFusion info
		var head = {};
		
		//head.index = index;
		//log('head.index: ' + head.index);
		head.thead = $(element);
		head.table = head.thead.closest('table');
		head.firstRow = head.table.find('tbody > tr:first-child');

		head.thead.find('th').not('.no-sort').addClass('fw0Sortable').each(processColumn);
		function processColumn(index, element) {
			var col = {};
			col.index = index; // Trying to scope every variable.
			col.th = $(element);
			//log('col.index: ' + col.index);
			col.firstCell = head.firstRow.children('td:eq(' + col.index + ')');
			col.diff = col.firstCell.text(); // The text of the first cell.
			col.ascending = true; // The entire column is in ascending order.
			col.hasClassDate = col.th.hasClass('date');
			col.hasClassNum = col.th.hasClass('num');
			col.differences = false; // If every single value is the same, then don't show a ascending arrow
			col.tr = head.table.find('tbody > tr');
			col.tr.each(processAscending);
			col.descending= false; // The entire column is in descening order.
			if (!col.ascending && col.differences) {
				col.descending = true;
				col.tr.each(processDescending);
			}
			if (col.differences) {
				if (col.ascending) {
					col.th.addClass('fw0Ascending').append('<span class="fw0Unicode">&#9650;</span>'); // http://www.charbase.com/25b3-unicode-white-up-pointing-triangle
				} else if (col.descending) {
					col.th.addClass('fw0Descending').append('<span class="fw0Unicode">&#9660;</span>'); // http://www.charbase.com/25bc-unicode-black-down-pointing-triangle
				} else {
					col.th.append('<span class="fw0Unicode">&#9650;</span>');
					col.th.find('.fw0Unicode').fadeTo('fast',0.01);
				}
			}
			function processAscending(index, element) {
				var row = {};
				
				row.index = index;
				row.tr = $(element);
				row.text = row.tr.children('td:eq(' + col.index + ')').text();
				if (col.hasClassNum) {
					row.text = row.text.replace(/,/g ,'');
					row.text = parseFloat(row.text);
				} else {
					row.text = $.trim(row.text);
				}
				if (col.hasClassDate) {
					row.text = row.tr.children('td:eq(' + col.index + ')').data('date') || row.text;
				}
				if (row.index) {
					if (row.text < col.text) {
						col.ascending = false;
						col.differences = true;
						return false;
					}
					if (row.text > col.text) {
						col.text = row.text;
						col.differences = true;
					}
				} else {
					col.text = row.text;
				}
			};

			function processDescending(index, element) {
				var row = {};
				
				row.index = index;
				row.tr = $(element);
				row.text = row.tr.children('td:eq(' + col.index + ')').text();
				if (col.hasClassNum) {
					row.text = row.text.replace(/,/g ,'');
					row.text = parseFloat(row.text);
				} else {
					row.text = $.trim(row.text);
				}
				if (col.hasClassDate) {
					row.text = row.tr.children('td:eq(' + col.index + ')').data('date') || row.text;
				}
				if (row.index) { // if we're not on row 1
					if (row.text > col.text) {
						col.descending = false;
						return false;
					}
				} else {
					col.text = row.text;
				}
			}
		};
	};
})();

(function() {
	$(document).on('click','th a',hyperlinkClicked);// if they put a hyperlink inside a th element
	function hyperlinkClicked(myEvent) {
		myEvent.stopPropagation(); // you don't want to sort the column when they choose the hyperlink.
	}
})();

(function() {
	$(document).on('click','th.fw0Sortable',thSortable); // Whenever any th.fw0Sortable is clicked
	function thSortable() {
		var col = {};
	
		col.th = $(this);
		col.table = col.th.closest('table');
		col.column = col.th.index();
		col.hasClassNum  = col.th.hasClass('num');
		col.hasClassDate = col.th.hasClass('date');
		col.rows = col.table.find('tbody > tr');
		
		col.hadClassAscending = col.th.hasClass('fw0Ascending');
	
		col.th.closest('tr').find('th').removeClass('fw0Descending fw0Ascending ').find('.fw0Unicode').each(function() {
			if ($(this).closest('th').index() !== col.th.index()) {
				$(this).fadeTo('fast',.01); // Everybody out!
			}
		});
		if (col.hadClassAscending) {
			col.th.addClass('fw0Descending').find('.fw0Unicode').html('&#9660;').fadeTo('fast',1);
			col.rows.sort(function(rowA, rowB) {
				return -sortAsc(rowA,rowB);
			});
		} else {
			col.th.addClass('fw0Ascending').find('.fw0Unicode').html('&#9650;').fadeTo('fast',1);
			col.rows.sort(sortAsc);
		}
		$.each(col.rows,function(index, element){
			col.table.children('tbody').append(element);
		});
		function sortAsc(rowA,rowB) {
			var row = {};
			
			row.a = $(rowA).children('td:eq(' + col.column + ')').text();
			row.b = $(rowB).children('td:eq(' + col.column + ')').text();
			row.a = $.trim(row.a); // probably not necessary
			row.b = $.trim(row.b); // probably not necessary
			if (col.hasClassDate) {
				row.a = $(rowA).children('td:eq(' + col.column + ')').data('date') || row.a;
				row.b = $(rowB).children('td:eq(' + col.column + ')').data('date') || row.b;
				return row.a - row.b;
			}
			if (col.hasClassNum) {
				row.a = row.a.replace(/,/g ,''); // strip commas
				row.b = row.b.replace(/,/g ,''); // strip commas
				return row.a - row.b;
			}
			if (row.a < row.b) return -1;
			if (row.a > row.b) return 1;
			return 0;
		}
	}
})();
