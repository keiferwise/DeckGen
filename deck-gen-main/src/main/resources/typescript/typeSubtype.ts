    // script.ts
    interface subtype {
      type_id: string;
	  sub_type_name: String;
	 sub_type_id: String;
    }
    declare const subtypes: subtype[];

    function filterOptions() {
  
  const cardType = document.getElementById('type') as HTMLSelectElement;
  const cardSubtype = document.getElementById('subtype') as HTMLSelectElement;
  const selectedCategory = cardType.value;

  const cardSubtypeOptions: HTMLOptionElement[] = [];
  
  subtypes.forEach((subtype) => {
    console.log(subtype.type_id);
    console.log(subtype.sub_type_id);
    console.log(subtype.sub_type_name);
  });
  
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