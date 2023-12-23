// script.ts
interface subtype {
  type_id: string;
	subtype_name: string;
	subtype_id: string;
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
  const cardSubtypeLabel = document.getElementById("subtypeLabel") as HTMLLabelElement;
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
  console.log(e.type_id + ", " +e.subtype_name + ", "+ e.subtype_id);

  if(e.type_id==currentTypeId){
    
    const option = document.createElement('option');
    
    option.value = e.subtype_id;
    
    option.text = e.subtype_name;
    console.log(e.subtype_id);
    cardSubtype.add(option);

  }
});

if(cardSubtype.options.length===0){
  cardSubtype.style.display = "none";
  cardSubtypeLabel.style.display = "none";
}
else{
  cardSubtype.style.display = "inline";
  cardSubtypeLabel.style.display = "inline";

}
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