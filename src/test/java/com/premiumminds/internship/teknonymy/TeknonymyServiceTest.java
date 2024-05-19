package com.premiumminds.internship.teknonymy;

import com.premiumminds.internship.teknonymy.TeknonymyService;
import com.premiumminds.internship.teknonymy.Person;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TeknonymyServiceTest {

  /**
   * The corresponding implementations to test.
   *
   * If you want, you can make others :)
   *
   */
  public TeknonymyServiceTest() {
  };

  @Test
  public void PersonNoChildrenTest() {
    Person person = new Person("John",'M',null, LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "";
    assertEquals(result, expected);
  }

  @Test
  public void PersonOneChildTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)) },
        LocalDateTime.of(1046, 1, 1, 0, 0));
    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "father of Holy";
    assertEquals(result, expected);
  }

  @Test
  public void PersonChildrenTest() {
    Person person = new Person(
        "Emma",
        'F',
        new Person[]{ 
            new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("David",'M', null, LocalDateTime.of(1045, 1, 1, 0, 0)),
            new Person("Anna",'F', null, LocalDateTime.of(1047, 1, 1, 0, 0))
        }, LocalDateTime.of(1026, 1, 1, 0, 0));

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "mother of David";
    assertEquals(result, expected);
  }

  @Test
  public void PersonLevelFiveOneDescendantTest() {
    Person person = new Person(
        "Emma",
        'F',
        new Person[]{ 
            new Person("Holy",'F', new Person[]{ 
                new Person("David",'M', new Person[]{ 
                    new Person("Anna",'F', new Person[]{ 
                        new Person("Fiora",'F', new Person[]{ 
                            new Person("Annie",'F', null, LocalDateTime.of(1050, 1, 1, 0, 0)) 
                        }, LocalDateTime.of(1049, 1, 1, 0, 0)) 
                    }, LocalDateTime.of(1048, 1, 1, 0, 0)) 
                }, LocalDateTime.of(1047, 1, 1, 0, 0)) 
            }, LocalDateTime.of(1046, 1, 1, 0, 0)) 
        }, LocalDateTime.of(1045, 1, 1, 0, 0)
    );

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-great-great-grandmother of Annie";
    assertEquals(result, expected);
  }

  @Test
  public void PersonLevelTwoOneDescendantTest() {
    Person person = new Person(
        "John",
        'M',
        new Person[]{ 
            new Person("Holy",'F', null, LocalDateTime.of(1046, 1, 1, 0, 0)),
            new Person("David",'M', new Person[]{
                new Person("Anna",'F', null, LocalDateTime.of(1100, 1, 1, 0, 0)) 
            }, LocalDateTime.of(1080, 1, 1, 0, 0)) 
        }, LocalDateTime.of(1026, 1, 1, 0, 0)
    );

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "grandfather of Anna";
    assertEquals(result, expected);
  }

  @Test
  public void PersonLevelThreeMultipleDescendantTest() {
    Person person = new Person(
        "Emma",
        'F',
        new Person[]{
            new Person("Holy",'F', new Person[]{ 
                new Person("Anna",'F', new Person[]{
                    new Person("Maria",'F', null, LocalDateTime.of(1099, 1, 1, 0, 0)),
                    new Person("Gabriel",'M', null, LocalDateTime.of(1100, 1, 1, 0, 0))
                }, LocalDateTime.of(1047, 1, 1, 0, 0))
            }, LocalDateTime.of(1047, 1, 1, 0, 0)),
            new Person("David",'M', new Person[]{ 
                new Person("Wei",'M', new Person[]{ 
                    new Person("Garen",'M', null, LocalDateTime.of(1098, 1, 1, 0, 0)),
                    new Person("Fiora",'F', null, LocalDateTime.of(1101, 1, 1, 0, 0))
                }, LocalDateTime.of(1047, 1, 1, 0, 0))
            }, LocalDateTime.of(1047, 1, 1, 0, 0)),
        }, LocalDateTime.of(1026, 1, 1, 0, 0)
    );

    String result = new TeknonymyService().getTeknonymy(person);
    String expected = "great-grandmother of Garen";
    assertEquals(result, expected);
  }
}