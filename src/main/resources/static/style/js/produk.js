let productCount = document.getElementById("productCount");
let countValue = 1;

document.writeln(productCount)

const btnPlus = () => {
    countValue++;
    productCount.innerHTML = countValue;
}