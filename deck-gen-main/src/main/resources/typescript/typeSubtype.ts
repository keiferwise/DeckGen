// script.ts
interface subtype {
  type_id: string;
	sub_type_name: string;
	sub_type_id: string;
}
interface type {
  type_id: string;
  type_name: string;
}
declare const subtypes: subtype[];
declare const types: type[];

function filterOptions() {
  
  const cardType = document.getElementById('type') as HTMLSelectElement;
  const cardSubtype = document.getElementById('subtype') as HTMLSelectElement;
  const selectedCategory = cardType.value;

  const cardSubtypeOptions: HTMLOptionElement[] = [];
  
  var currentTypeName = cardType.value;
  console.log(currentTypeName);
  var currentTypeId = '';
  types.forEach((t)=>{
    if(t.type_name==currentTypeName){
      currentTypeId=t.type_id
    }
  });

while (cardSubtype.options.length > 0) {
  cardSubtype.remove(0);
}
subtypes.forEach(e => {
  if(e.type_id==currentTypeId){
    
    const option = document.createElement('option');
    
    option.value = e.sub_type_id;
    
    option.text = e.sub_type_name;
    
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