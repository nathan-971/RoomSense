function showPopup(message)
{
    const existing = document.getElementById("messageModal");
    if (existing)
    {
         existing.remove();
    }

    const html = `
        <div class="modal fade" id="messageModal" tabindex="-1" aria-labelledby="messageModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTitle">Error Occurred</h5>
                    </div>
                    <div class="modal-body">
                        <p id="message">${message}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    `

    document.body.insertAdjacentHTML("beforeend", html);
    $('#messageModal').modal('show');
    $('#messageModal').on('hidden.bs.modal', function () {
        $(this).remove();
    });
}