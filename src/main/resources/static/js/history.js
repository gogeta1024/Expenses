    // Lấy dữ liệu từ SessionStorage
    document.addEventListener("DOMContentLoaded", () => {
        const purchaseDate = sessionStorage.getItem("purchaseDate");
        const supermarket = sessionStorage.getItem("supermarket");
        const type = sessionStorage.getItem("type");

        if (purchaseDate) document.querySelector("#purchaseDate").value = purchaseDate;
        if (supermarket) document.querySelector("#supermarket").value = supermarket;
        if (type) document.querySelector("#type").value = type;
    });

    // Lưu dữ liệu vào SessionStorage khi ấn "Save"
    document.querySelector("form").addEventListener("submit", () => {
        const purchaseDate = document.querySelector("#purchaseDate").value;
        const supermarket = document.querySelector("#supermarket").value;
        const type = document.querySelector("#type").value;

        sessionStorage.setItem("purchaseDate", purchaseDate);
        sessionStorage.setItem("supermarket", supermarket);
        sessionStorage.setItem("type", type);
    });