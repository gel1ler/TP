import game.Battle;
import game.Map.MainMap;
import game.Player.OwnerType;
import game.Player.Entities.Hero;
import game.Player.Entities.HeroType;
import game.Player.Entities.Unit;
import game.Player.Entities.UnitType;
import game.Player.Player;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;

import static junit.framework.TestCase.*;

public class BattleTest {
    private Player murderer, victim;
    private Hero murdererHero, victimHero;
    private Unit MHUnit, VHUnit;
    private MainMap gameMap;
    private Battle battle;

    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        murderer = new Player(185, OwnerType.PERSON);
        victim = new Player(185, OwnerType.COMPUTER);
        murdererHero = new Hero(HeroType.BARBARIAN, OwnerType.PERSON);
        victimHero = new Hero(HeroType.BARBARIAN, OwnerType.COMPUTER);
        MHUnit = new Unit(UnitType.SWORDSMAN, OwnerType.PERSON);
        VHUnit = new Unit(UnitType.CAVALRYMAN, OwnerType.COMPUTER);
        murdererHero.addUnit(MHUnit);
        victimHero.addUnit(VHUnit);
        gameMap = new MainMap(5, 5, murderer, victim);
        battle = new Battle(5, 5, murderer, victim, murdererHero, victimHero, gameMap);

        outputStream = new ByteArrayOutputStream();
    }

    @Test
    public void attackTest() {
        TestUtils.setOutputStream(outputStream);
        assertEquals(60, VHUnit.getHp());
        battle.attack(MHUnit, VHUnit);

        assertTrue(TestUtils.logsContains(outputStream, "У вражеского Юнита Кавалерист осталось 45 HP"));
        assertEquals(45, VHUnit.getHp());
    }

    @Test
    public void killTest() {
        TestUtils.setOutputStream(outputStream);
        assertTrue(VHUnit.getIsAlive());
        for (int i = 0; i < 4; i++) {
            battle.attack(MHUnit, VHUnit);
        }

        assertTrue(TestUtils.logsContains(outputStream, "Юнит Кавалерист убит"));
        assertFalse(TestUtils.logsContains(outputStream, "Юнит Паладин убит"));
        assertFalse(VHUnit.getIsAlive());
    }

    @Test
    public void superAbilityTest() {
        TestUtils.setOutputStream(outputStream);

        int[] coords = {2, 3};
        Unit rascal = new Unit(UnitType.RASCAL, OwnerType.PERSON);
        VHUnit.setPos(coords);
        murdererHero.addUnit(rascal);

        assertEquals(OwnerType.COMPUTER, VHUnit.getOwner());

        while (VHUnit.getOwner() == OwnerType.COMPUTER) {
            battle.useSuperAbility(rascal, VHUnit);
        }

        assertTrue(TestUtils.logsContains(outputStream, "Кавалерист успешно завербован!"));
        assertEquals(OwnerType.PERSON, VHUnit.getOwner());
    }

    @Test
    public void distanceTest() {
        //Дальность мечника = 1
        battle.setEntityPos(MHUnit, battle.getMap(), new int[]{2, 3});
        battle.setEntityPos(VHUnit, battle.getMap(), new int[]{2, 2});
        assertNotNull(battle.canPersonAttack(MHUnit.getPos(), MHUnit.getFightDist()));

        battle.setEntityPos(VHUnit, battle.getMap(), new int[]{1, 1});
        assertNull(battle.canPersonAttack(MHUnit.getPos(), MHUnit.getFightDist()));

        //Дальность арбалетчика = 3
        MHUnit = new Unit(UnitType.CROSSBOWMAN, OwnerType.PERSON);
        battle.setEntityPos(MHUnit, battle.getMap(), new int[]{2, 3});
        battle.setEntityPos(VHUnit, battle.getMap(), new int[]{2, 2});
        assertNotNull(battle.canPersonAttack(MHUnit.getPos(), MHUnit.getFightDist()));

        battle.setEntityPos(VHUnit, battle.getMap(), new int[]{0, 1});
        assertNotNull(battle.canPersonAttack(MHUnit.getPos(), MHUnit.getFightDist()));
    }
}
