package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.Person;

import java.util.LinkedList;
import java.util.Map;
import java.util.AbstractMap;

class TeknonymyService implements ITeknonymyService {

  /**
   * Method to get a Person Teknonymy Name
   * 
   * @param Person person
   * @return String which is the Teknonymy Name 
   */
  public String getTeknonymy(Person person) {
    if (person.children() == null || person.children().length == 0)
      return "";

    LinkedList<Map.Entry<Integer, Person>> fifo = new LinkedList<>();
    fifo.add(new AbstractMap.SimpleEntry<>(0, person));

    Map.Entry<Integer, Person> pair;
    Person target = null, element;
    int lvl = -1, elementLvl = -1;

    while (!fifo.isEmpty()) {
      pair = fifo.removeFirst();
      elementLvl = pair.getKey(); element = pair.getValue();

      if (lvl < elementLvl) {
        lvl = elementLvl;
        target = element;
      } else if (element.dateOfBirth().isBefore(target.dateOfBirth())) {
        target = element;
      }

      if (element.children() != null) {
        for (Person child : element.children())
          fifo.add(new AbstractMap.SimpleEntry<>(lvl+1, child));
      }
    }
    
    return getTeknonymyAux(lvl, person, target);
  };

  /**
   * Auxiliar method to convert the relations to Teknonymy Name.
   * @param int lvl
   * @param Person root
   * @param Person leaf
   * @return String which is the Teknonymy Name
   */
  public String getTeknonymyAux(int lvl, Person root, Person leaf) {
    if (lvl == 0 || root == null || leaf == null)
      return "";
    
    String name = leaf.name(), prefix = "", middleTerm = (root.sex() == 'F') ? "mother of " : "father of ";

    if (lvl > 2) {
      for (int i = 3; i <= lvl; i++)
        prefix += "great-";
    }
    if (lvl > 1)
      prefix += "grand";

    return prefix + middleTerm + name;
  };
}
