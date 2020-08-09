// $(document).ready(function () {
$(function () {
    makeEditable({
            ajaxUrl: "ui/meals/",
            datatableApi: $("#datatable").DataTable({
                "paging": false,
                "info": true,
                "columns": [
                    {
                        "data": "dateTime"
                    },
                    {
                        "data": "description"
                    },
                    {
                        "data": "calories"
                    },
                    {
                        "defaultContent": "Edit",
                        "orderable": false
                    },
                    {
                        "defaultContent": "Delete",
                        "orderable": false
                    }
                ],
                "order": [
                    [
                        0,
                        "desc"
                    ]
                ]
            })
        }
    );
});

function filter() {
    const datatable = $("#datatable").DataTable();
    $.get("ui/meals/filter?"+filterForm.serialize(), function(newDataArray) {
        datatable.clear();
        datatable.rows.add(newDataArray);
        datatable.draw();
    });
}