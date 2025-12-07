// search.js

function handleSearch() {
    const query = document.getElementById('searchInput').value.toLowerCase().trim();
    const resultsDiv = document.getElementById('searchResults');
    resultsDiv.innerHTML = '';

    if (!query) {
        resultsDiv.style.display = 'none';
        return;
    }

    const matches = window.allWatches.filter(p => {
        return (p.name || '').toLowerCase().includes(query) ||
               (p.brand || '').toLowerCase().includes(query) ||
               (p.price || '').toString().includes(query);
    });

    if (matches.length === 0) {
        resultsDiv.innerHTML = '<div style="padding:10px;color:#555;">No results found</div>';
    } else {
        matches.forEach(p => {
            const div = document.createElement('div');
            div.style.padding = '10px';
            div.style.borderBottom = '1px solid #eee';
            div.style.cursor = 'pointer';
            div.innerText = `${p.name} — ₹${p.price.toLocaleString()}`;
            div.onclick = () => { 
                openModalFromData(p); 
                resultsDiv.style.display = 'none';
                document.getElementById('searchInput').value = '';
            };
            resultsDiv.appendChild(div);
        });
    }
    resultsDiv.style.display = 'block';
}

// Hide results if clicking outside
document.addEventListener('click', (e) => {
    if (!document.getElementById('searchInput').contains(e.target) &&
        !document.getElementById('searchResults').contains(e.target)) {
        document.getElementById('searchResults').style.display = 'none';
    }
});
