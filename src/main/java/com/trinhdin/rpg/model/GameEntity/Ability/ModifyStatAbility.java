package com.trinhdin.rpg.model.GameEntity.Ability;
import com.trinhdin.rpg.model.GameEntity.Character.Character;
import com.trinhdin.rpg.model.GameEntity.Character.Stat;
public class ModifyStatAbility extends Ability{
    private Stat stat;

    public ModifyStatAbility(String name, int cost, int cooldown, Stat stat) {
        super(name, cost, cooldown);
        this.stat = stat;
    }

    @Override
    public void use(Character caster, Character target) {
        if(caster.getCurrentMana() < cost) {
            System.out.println("Not enough mana");
        }else{
            target.getStat().modifyStat(stat);
            caster.setCurrentMana(caster.getCurrentMana() - cost);
        }
    }
}
