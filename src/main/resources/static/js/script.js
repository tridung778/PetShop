const inputLeft = document.getElementById("input-left");
const inputRight = document.getElementById("input-right");
const range1Input = document.getElementById("range1Input");
const range2Input = document.getElementById("range2Input");

const content = document.getElementById("content");
const processBar = document.getElementById("process-bar");

const docEl = document.documentElement;

const thumbLeft = document.querySelector(".slider > .thumb.left");
const thumbRight = document.querySelector(".slider > .thumb.right");
const range = document.querySelector(".slider > .range");

document.addEventListener('scroll', () => {
    const scrollTop = window.scrollY || docEl.scrollTop;
    const scrollHeight = docEl.scrollHeight - window.innerHeight;
    const percent = (scrollTop / scrollHeight) * 100;
    processBar.style.width = percent + '%';

})

let mybutton = document.getElementById("myBtn");

// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function () {
    scrollFunction()
};

function scrollFunction() {
    if (document.body.scrollTop > 20 || docEl.scrollTop > 20) {
        mybutton.style.display = "block";
    } else {
        mybutton.style.display = "none";
    }
}

function topFunction() {
    document.body.scrollTo({
        top: 0,
        behavior: "smooth"
    })
}

function formatCurrency(value) {
    const formatter = new Intl.NumberFormat('vi-VN', {
        style: 'currency',
        currency: 'VND',
        minimumFractionDigits: 0
    });
    return formatter.format(value);
}

const setLeftValue = () => {
    const _this = inputLeft;
    const [min, max] = [parseInt(_this.min), parseInt(_this.max)];

    _this.value = Math.min(parseInt(_this.value), parseInt(inputRight.value) - 1);

    const percent = ((_this.value - min) / (max - min)) * 100;
    thumbLeft.style.left = percent + "%";
    range.style.left = percent + "%";

    const formattedValue = formatCurrency(_this.value * 1000000); // Format giá trị
    document.querySelector('label[for="range1Input"]').textContent = formattedValue; // Cập nhật label
    range1Input.value = _this.value; // Cập nhật input ẩn
};

const setRightValue = () => {
    const _this = inputRight;
    const [min, max] = [parseInt(_this.min), parseInt(_this.max)];

    _this.value = Math.max(parseInt(_this.value), parseInt(inputLeft.value) + 1);

    const percent = ((_this.value - min) / (max - min)) * 100;
    thumbRight.style.right = 100 - percent + "%";
    range.style.right = 100 - percent + "%";

    const formattedValue = formatCurrency(_this.value * 1000000);
    document.querySelector('label[for="range2Input"]').textContent = formattedValue;
    range2Input.value = _this.value;
};

inputLeft.addEventListener("input", setLeftValue);
inputRight.addEventListener("input", setRightValue);

// Khởi tạo giá trị ban đầu
setLeftValue();
setRightValue();

//Them san pham vao gio hang
function addToCart(id, quantity) {
    // Ngăn form gửi yêu cầu mặc định
    event.preventDefault();

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/addToCart/" + id, true);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                // Cập nhật số lượng sản phẩm trong giỏ hàng
                var response = JSON.parse(xhr.responseText);
                document.getElementById('cart-count').innerText = response.animalIndex + ' sản phẩm';
                $.notify(
                    "Thêm vào giỏ hàng thành công!",
                    {className: "success", position: "top left"}
                );
            } else {
                swal("Thông báo!", "Vui lòng đăng nhập để sử dụng dịch vụ!", "info");
            }
        }
    };
    xhr.send("quantity=" + quantity);
}

// Xu ly filter va sort
function redirect(field) {
    if (field !== '') {
        let url = document.URL
        url = removePriceOrderParams(url)
        let sortField = 'price';
        let sortOrder = field.endsWith('_asc') ? 'ASC' : 'DESC';
        url += '&field=' + sortField;
        url += '&order=' + sortOrder;
        console.log(url)
        window.location.href = url;
    }
}

function removePriceOrderParams(url) {
    let urlObj = new URL(url);
    urlObj.searchParams.delete('field');
    urlObj.searchParams.delete('order');
    return urlObj.toString();
}





