const products = [
    {
        id: 1,
        name: "Meditative Shiva",
        category: "deity",
        price: 699,
        badge: "Bestseller",
        description: "Lord Shiva in deep meditation — intricately detailed 3D printed statue with premium matte black finish.",
        image: "images/shiva.jpg"
    },
    {
        id: 2,
        name: "Radha Krishna Pair",
        category: "deity",
        price: 599,
        badge: "New",
        description: "Divine Radha Krishna duo figure with antique silver finish. Perfect for home altar or gifting.",
        image: "images/radha-krishna.svg"
    },
    {
        id: 3,
        name: "Serene Buddha Bust",
        category: "deity",
        price: 599,
        badge: "",
        description: "Peaceful Buddha bust in pristine white. Elegant decor piece for meditation rooms and living spaces.",
        image: "images/buddha.jpg"
    },
    {
        id: 4,
        name: "Custom Action Figure",
        category: "action",
        price: 899,
        badge: "Custom",
        description: "Personalized 3D printed action figure — your design, your pose, your character brought to life.",
        image: "images/action-figure.svg"
    },
    {
        id: 5,
        name: "Anime Character Model",
        category: "action",
        price: 999,
        badge: "",
        description: "High-detail anime figure, fully painted. Multiple sizes and characters available on request.",
        image: "images/anime-figure.svg"
    },
    {
        id: 6,
        name: "3D Printable STL Bundle",
        category: "model",
        price: 499,
        badge: "Digital",
        description: "Downloadable STL files for 3D printing at home. Multiple deity and figure designs included.",
        image: "images/stl-bundle.svg"
    },
    {
        id: 7,
        name: "Ganesha Idol",
        category: "deity",
        price: 199,
        badge: "",
        description: "Lord Ganesha seated idol with gold accent finish. Ideal for festivals, pooja rooms, and gifts.",
        image: "images/ganesha.svg"
    },
    {
        id: 8,
        name: "Spider-Man Bust",
        category: "action",
        price: 1299,
        badge: "Bestseller",
        description: "Detailed Spider-Man bust with iconic web pattern. Premium 3D printed finish on display base.",
        image: "images/spiderman.jpg"
    },
    {
        id: 9,
        name: "Iron Man Bust",
        category: "action",
        price: 1499,
        badge: "New",
        description: "Iron Man Mark bust with arc reactor detail. Marble-effect finish on classic display pedestal.",
        image: "images/ironman.jpg"
    },
    {
        id: 9,
        name: "Decorative Model Pack",
        category: "model",
        price: 799,
        badge: "Digital",
        description: "Collection of decorative 3D models — animals, fantasy creatures & abstract art. Instant download.",
        image: "images/model-pack.svg"
    }
];

let cart = JSON.parse(localStorage.getItem('vision3d_cart')) || [];
let currentFilter = 'all';

function formatPrice(price) {
    return '\u20B9' + price.toLocaleString('en-IN');
}

function renderProducts(filter = 'all') {
    const grid = document.getElementById('productGrid');
    const filtered = filter === 'all' ? products : products.filter(p => p.category === filter);

    if (filtered.length === 0) {
        grid.innerHTML = '<p class="no-products">No products found in this category.</p>';
        return;
    }

    grid.innerHTML = filtered.map(p => `
        <div class="product-card" data-category="${p.category}">
            <div class="product-image">
                ${p.badge ? `<span class="product-badge">${p.badge}</span>` : ''}
                <img src="${p.image}" alt="${p.name}" loading="lazy" onerror="this.src='data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 400 400%22><rect fill=%22%231a1a2e%22 width=%22400%22 height=%22400%22/><text fill=%22%237c3aed%22 font-size=%2218%22 x=%2250%25%22 y=%2250%25%22 text-anchor=%22middle%22>${p.name}</text></svg>'">
            </div>
            <div class="product-info">
                <h3>${p.name}</h3>
                <p>${p.description}</p>
                <div class="product-footer">
                    <span class="product-price">${formatPrice(p.price)}</span>
                    <button class="add-to-cart" onclick="addToCart(${p.id})">Add to Cart</button>
                </div>
            </div>
        </div>
    `).join('');
}

function addToCart(productId) {
    const existing = cart.find(item => item.id === productId);
    if (existing) {
        existing.qty++;
    } else {
        const product = products.find(p => p.id === productId);
        cart.push({ ...product, qty: 1 });
    }
    saveCart();
    updateCartUI();
    showToast('Added to cart!');
}

function removeFromCart(productId) {
    cart = cart.filter(item => item.id !== productId);
    saveCart();
    updateCartUI();
}

function updateQty(productId, delta) {
    const item = cart.find(item => item.id === productId);
    if (item) {
        item.qty += delta;
        if (item.qty <= 0) {
            removeFromCart(productId);
            return;
        }
    }
    saveCart();
    updateCartUI();
}

function saveCart() {
    localStorage.setItem('vision3d_cart', JSON.stringify(cart));
}

function updateCartUI() {
    const count = cart.reduce((sum, item) => sum + item.qty, 0);
    document.getElementById('cartCount').textContent = count;
    renderCartItems();
}

function renderCartItems() {
    const container = document.getElementById('cartItems');
    const totalEl = document.getElementById('cartTotal');
    const checkoutBtn = document.getElementById('checkoutBtn');

    if (cart.length === 0) {
        container.innerHTML = '<p class="cart-empty">Your cart is empty.<br>Add some amazing figures!</p>';
        totalEl.textContent = formatPrice(0);
        checkoutBtn.disabled = true;
        checkoutBtn.style.opacity = '0.5';
        checkoutBtn.style.cursor = 'not-allowed';
        return;
    }

    checkoutBtn.disabled = false;
    checkoutBtn.style.opacity = '1';
    checkoutBtn.style.cursor = 'pointer';

    container.innerHTML = cart.map(item => `
        <div class="cart-item">
            <div class="cart-item-image">
                <img src="${item.image}" alt="${item.name}" onerror="this.src='data:image/svg+xml,<svg xmlns=%22http://www.w3.org/2000/svg%22 viewBox=%220 0 60 60%22><rect fill=%22%231a1a2e%22 width=%2260%22 height=%2260%22/></svg>'">
            </div>
            <div class="cart-item-details">
                <h4>${item.name}</h4>
                <span class="cart-item-price">${formatPrice(item.price)}</span>
            </div>
            <div class="cart-item-qty">
                <button onclick="updateQty(${item.id}, -1)">-</button>
                <span>${item.qty}</span>
                <button onclick="updateQty(${item.id}, 1)">+</button>
            </div>
            <button class="cart-item-remove" onclick="removeFromCart(${item.id})">&times;</button>
        </div>
    `).join('');

    const total = cart.reduce((sum, item) => sum + item.price * item.qty, 0);
    totalEl.textContent = formatPrice(total);
}

function showToast(message) {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.classList.add('show');
    setTimeout(() => toast.classList.remove('show'), 2500);
}

function openCart() {
    document.getElementById('cartSidebar').classList.add('open');
    document.getElementById('cartOverlay').classList.add('open');
}

function closeCart() {
    document.getElementById('cartSidebar').classList.remove('open');
    document.getElementById('cartOverlay').classList.remove('open');
}

function handleCheckout() {
    if (cart.length === 0) return;
    const total = cart.reduce((sum, item) => sum + item.price * item.qty, 0);
    const itemNames = cart.map(item => `${item.name} x${item.qty}`).join(', ');
    const message = encodeURIComponent(`Hi Vision 3D Studio! I'd like to order:\n\n${itemNames}\n\nTotal: ${formatPrice(total)}\n\nPlease confirm my order.`);
    window.open(`https://wa.me/919370893238?text=${message}`, '_blank');
}

function handleContactForm(e) {
    e.preventDefault();
    const name = document.getElementById('name').value;
    const email = document.getElementById('email').value;
    const message = document.getElementById('message').value;
    const status = document.getElementById('formStatus');
    const fullMessage = encodeURIComponent(`Hi, I'm ${name} (${email}).\n\n${message}`);
    window.open(`https://wa.me/919370893238?text=${fullMessage}`, '_blank');
    status.textContent = 'Opening WhatsApp...';
    setTimeout(() => { status.textContent = ''; e.target.reset(); }, 3000);
}

function handleFilterClick(e) {
    const btn = e.target.closest('.filter-btn');
    if (!btn) return;
    document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
    btn.classList.add('active');
    currentFilter = btn.dataset.filter;
    renderProducts(currentFilter);
}

function handleCategoryClick(e) {
    const card = e.target.closest('.category-card');
    if (!card) return;
    currentFilter = card.dataset.category;
    document.querySelectorAll('.filter-btn').forEach(b => {
        b.classList.toggle('active', b.dataset.filter === currentFilter);
    });
    renderProducts(currentFilter);
    document.getElementById('products').scrollIntoView({ behavior: 'smooth' });
}

function initNavbar() {
    const navbar = document.getElementById('navbar');
    window.addEventListener('scroll', () => {
        navbar.classList.toggle('scrolled', window.scrollY > 50);
    });
}

function initMobileMenu() {
    const hamburger = document.getElementById('hamburger');
    const navLinks = document.getElementById('navLinks');
    hamburger.addEventListener('click', () => {
        navLinks.classList.toggle('open');
    });
    navLinks.querySelectorAll('a').forEach(link => {
        link.addEventListener('click', () => navLinks.classList.remove('open'));
    });
}

document.addEventListener('DOMContentLoaded', () => {
    renderProducts();
    updateCartUI();
    initNavbar();
    initMobileMenu();

    document.getElementById('cartBtn').addEventListener('click', openCart);
    document.getElementById('cartClose').addEventListener('click', closeCart);
    document.getElementById('cartOverlay').addEventListener('click', closeCart);
    document.getElementById('checkoutBtn').addEventListener('click', handleCheckout);
    document.getElementById('contactForm').addEventListener('submit', handleContactForm);

    document.querySelector('.filter-bar').addEventListener('click', handleFilterClick);
    document.querySelector('.category-grid').addEventListener('click', handleCategoryClick);

    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape') closeCart();
    });
});