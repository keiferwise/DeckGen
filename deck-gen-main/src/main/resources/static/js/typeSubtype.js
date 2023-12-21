function filterOptions() {
    var cardType = document.getElementById('type');
    var cardSubtype = document.getElementById('subtype');
    var selectedCategory = cardType.value;
    var cardSubtypeOptions = [];
    var currentTypeName = cardType.value;
    console.log(currentTypeName);
    var currentTypeId = '';
    types.forEach(function (t) {
        if (t.type_name == currentTypeName) {
            currentTypeId = t.type_id;
        }
    });
    while (cardSubtype.options.length > 0) {
        cardSubtype.remove(0);
    }
    subtypes.forEach(function (e) {
        console.log(e.type_id + ", " + e.subtype_name + ", " + e.subtype_id);
        if (e.type_id == currentTypeId) {
            var option = document.createElement('option');
            option.value = e.subtype_id;
            option.text = e.subtype_name;
            console.log(e.subtype_id);
            cardSubtype.add(option);
        }
    });
    //subtypes.forEach((subtype) => {
    //  console.log(subtype.type_id);
    //  console.log(subtype.sub_type_id);
    //  console.log(subtype.sub_type_name);
    //});
    //for (let i = 0; i < cardSubtype.options.length; i++) {
    //  cardSubtypeOptions.push(cardSubtype.options[i]);
    //  console.log(cardSubtype.options[i]);
    //}
    //  for (const st of subtypes){
    //    console.log(st.subtype_name);
    //  }
}
// Reset cardTSubtype options
// cardSubtype.innerHTML = '';
// Filter options based on selected category
//for (const option of cardSubtypeOptions) {
//  if (option.dataset.category === selectedCategory || selectedCategory === '') {
//    cardSubtype.appendChild(option.cloneNode(true));
// }
//}
//}
// Store original options of cardTSubtype
//const cardSubtype = document.getElementById('subtype') as HTMLSelectElement;
//const cardSubtypeOptions = Array.from(cardSubtype.options) as HTMLOptionElement[];
