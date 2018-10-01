/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2018 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.gfpixel.gfpixeldungeon.actors.mobs;

import com.watabou.utils.Random;

import java.util.ArrayList;
import java.util.Arrays;

public class Bestiary {
	/*
	public static ArrayList<Class<? extends Mob>> getMobRotation( int depth ) {

		ArrayList<Class<? extends Mob>> mobs = getRotation( depth % 5 );
		addRareMobs(depth, mobs);
		swapMobAlts(mobs);
		Random.shuffle(mobs);
		return mobs;
	}
	*/

	public static final MobRotations MR_SEWER;
	public static final MobRotations MR_PRISON;
	public static final MobRotations MR_HUNTINGRABBIT;
	public static final MobRotations MR_CAVES;
	public static final MobRotations MR_CITY;
	public static final MobRotations MR_RECAVES;
	public static final MobRotations MR_HALLS;


	static {

		MR_SEWER								= new MobRotations( 0 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch ( floor % 5 ) {
					case 0: default:
						return null;
					case 1:
						//10x rat
						return new ArrayList<Class<? extends Mob>>(Arrays.asList(
								Rat.class, Rat.class, Rat.class, Rat.class, Rat.class,
								Rat.class, Rat.class, Rat.class, Rat.class, Rat.class));
					case 2:
						//3x rat, 3x gnoll
						return new ArrayList<>(Arrays.asList(
								Rat.class, Rat.class, Rat.class,
								Gnoll.class, Gnoll.class, Gnoll.class));
					case 3:
						//2x rat, 4x gnoll, 1x crab, 1x swarm
						return new ArrayList<>(Arrays.asList(
								Rat.class, Rat.class,
								Gnoll.class, Gnoll.class, Gnoll.class, Gnoll.class,
								Crab.class, Swarm.class));
					case 4:
						//1x rat, 2x gnoll, 3x crab, 1x swarm
						return new ArrayList<>(Arrays.asList(
								Rat.class,
								Gnoll.class, Gnoll.class,
								Crab.class, Crab.class, Crab.class,
								Swarm.class));
				}
			}
		};
		MR_PRISON		= new MobRotations( 1 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor % 5) {
					case 0:default:
						return null;
					case 1:
						//3x skeleton, 1x thief, 1x swarm
						return new ArrayList<>(Arrays.asList(
								Skeleton.class, Skeleton.class, Skeleton.class,
								Thief.class,
								Swarm.class));
					case 2:
						//3x skeleton, 1x thief, 1x shaman, 1x guard
						return new ArrayList<>(Arrays.asList(
								Skeleton.class, Skeleton.class, Skeleton.class,
								Thief.class,
								Shaman.class,
								Guard.class));
					case 3:
						//3x skeleton, 1x thief, 2x shaman, 2x guard
						return new ArrayList<>(Arrays.asList(
								Skeleton.class, Skeleton.class, Skeleton.class,
								Thief.class,
								Shaman.class, Shaman.class,
								Guard.class, Guard.class));
					case 4:
						//3x skeleton, 1x thief, 2x shaman, 3x guard
						return new ArrayList<>(Arrays.asList(
								Skeleton.class, Skeleton.class, Skeleton.class,
								Thief.class,
								Shaman.class, Shaman.class,
								Guard.class, Guard.class, Guard.class));
				}
			}
		};
		MR_HUNTINGRABBIT   = new MobRotations( 1 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor % 5) {
					case 0:default:
						return null;
					case 1:
						return new ArrayList<>(Arrays.asList(
                                Skeleton.class, Skeleton.class, Skeleton.class,
								Ripper.class, Ripper.class,
								Shaman.class));
					case 2:
						return new ArrayList<>(Arrays.asList(
                                Skeleton.class, Skeleton.class, Skeleton.class,
								Ripper.class, Ripper.class,
								Shaman.class,
								Jaguar.class));
					case 3:
						return new ArrayList<>(Arrays.asList(
                                Skeleton.class, Skeleton.class,
								Ripper.class, Ripper.class,
								Shaman.class,
								Jaguar.class, Jaguar.class));
					case 4:
						return new ArrayList<>(Arrays.asList(
                                Skeleton.class, Skeleton.class,
								Ripper.class, Ripper.class,Ripper.class,
								Shaman.class,
								Jaguar.class, Jaguar.class));
				}
			}
		};
		MR_CAVES			= new MobRotations( 2 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor % 5) {
					case 0:default:
						return null;
					case 1:
						//5x bat, 1x brute
						return new ArrayList<>(Arrays.asList(
								Bat.class, Bat.class, Bat.class, Bat.class, Bat.class,
								Brute.class));
					case 2:
						//5x bat, 5x brute, 1x spinner
						return new ArrayList<>(Arrays.asList(
								Bat.class, Bat.class, Bat.class, Bat.class, Bat.class,
								Brute.class, Brute.class, Brute.class, Brute.class,
								Spinner.class,
                                Nemeum.class));
					case 3:
						//1x bat, 3x brute, 1x shaman, 1x spinner
						return new ArrayList<>(Arrays.asList(
								Bat.class,
								Brute.class, Brute.class, Brute.class,
                                Spinner.class,
                                Nemeum.class));
					case 4:
						return new ArrayList<>(Arrays.asList(
								Bat.class,
								Brute.class, Brute.class, Brute.class,
                                Spinner.class,Spinner.class,
                                Nemeum.class,Nemeum.class));
				}
			}
		};
		MR_CITY			= new MobRotations( 3 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor % 5) {
					case 0:default:
						return null;
					case 1:
						//5x elemental, 5x warlock, 1x monk
						return new ArrayList<>(Arrays.asList(
								Elemental.class, Elemental.class, Elemental.class, Elemental.class, Elemental.class,
								Warlock.class, Warlock.class, Warlock.class, Warlock.class, Warlock.class,
								Monk.class));
					case 2:
						//2x elemental, 2x warlock, 2x monk
						return new ArrayList<>(Arrays.asList(
								Elemental.class, Elemental.class,
								Warlock.class, Warlock.class,
								Monk.class, Monk.class));
					case 3:
						//1x elemental, 1x warlock, 2x monk, 1x golem
						return new ArrayList<>(Arrays.asList(
								Elemental.class,
								Warlock.class,
								Monk.class, Monk.class,
								Golem.class));
					case 4:
						//1x elemental, 1x warlock, 2x monk, 3x golem
						return new ArrayList<>(Arrays.asList(
								Elemental.class,
								Warlock.class,
								Monk.class, Monk.class,
								Golem.class, Golem.class, Golem.class));
				}
			}
		};
		MR_RECAVES		= new MobRotations( 4 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor) {
					case 0: case 1: default:
						return null;
					case 2:
					case 3:
						//3x Goliath, 4x Aegis, 2x Jupiter, 1x Scorpio
						return new ArrayList<>(Arrays.asList(
								Goliath.class, Goliath.class, Goliath.class,
								Aegis.class, Aegis.class, Aegis.class, Aegis.class,
								Jupiter.class, Jupiter.class));
					case 4:
						return new ArrayList<>(Arrays.asList(
								Goliath.class, Goliath.class, Goliath.class, Goliath.class,
								Aegis.class, Aegis.class, Aegis.class,
								Jupiter.class, Jupiter.class));
				}
			}
		};
		MR_HALLS			= new MobRotations( 5 ) {
			@Override
			public ArrayList<Class<? extends Mob>> getRotation(int floor) {
				switch (floor) {
					case 0:default:
						return null;
					case 1:
						return new ArrayList<>(Arrays.asList(
								Cyclops.class, Cyclops.class,Cyclops.class, Cyclops.class,
								Succubus.class,Succubus.class,
								Jupiter.class));
					case 2:
						return new ArrayList<>(Arrays.asList(
								Cyclops.class, Cyclops.class,Cyclops.class,
								Succubus.class,Succubus.class,
								Jupiter.class, Jupiter.class,
								Hydra.class));
					case 3:
						return new ArrayList<>(Arrays.asList(
								Cyclops.class, Cyclops.class,
								Succubus.class, Jupiter.class,
								Hydra.class, Hydra.class, Hydra.class,
								Hydra.class, Hydra.class, Hydra.class));
					case 4:
						return new ArrayList<>(Arrays.asList(
								Cyclops.class,
								Hydra.class, Hydra.class, Hydra.class,
								Hydra.class, Hydra.class, Hydra.class,
								Hydra.class, Hydra.class, Hydra.class));
				}
			}
		};
	}
	/*
	//has a chance to add a rarely spawned mobs to the rotation
	public static void addRareMobs( int depth, ArrayList<Class<?extends Mob>> rotation ){

		switch (depth){
			// Sewers
			default:
				return;
			case 4:
				if (Random.Float() < 0.01f) rotation.add(Skeleton.class);
				if (Random.Float() < 0.01f) rotation.add(Thief.class);
				return;
				
			// Prison
			case 6:
				if (Random.Float() < 0.2f)  rotation.add(Shaman.class);
				return;
			case 8:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				return;
			case 9:
				if (Random.Float() < 0.02f) rotation.add(Bat.class);
				if (Random.Float() < 0.01f) rotation.add(Brute.class);
				return;
				
			// Caves
			case 13:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				return;
			case 14:
				if (Random.Float() < 0.02f) rotation.add(Elemental.class);
				if (Random.Float() < 0.01f) rotation.add(Monk.class);
				return;
				
			// City
			case 19:
				if (Random.Float() < 0.02f) rotation.add(Succubus.class);
				return;
			case 24:
				if (Random.Float() < 0.02f) rotation.add(Hydra.class);
				return;
		}
	}

	//switches out regular mobs for their alt versions when appropriate
	private static void swapMobAlts(ArrayList<Class<?extends Mob>> rotation){
		for (int i = 0; i < rotation.size(); i++){
			if (Random.Int( 50 ) == 0) {
				Class<? extends Mob> cl = rotation.get(i);
				if (cl == Rat.class) {
					cl = Albino.class;
				} else if (cl == Thief.class) {
					cl = Bandit.class;
				} else if (cl == Brute.class) {
					cl = Shielded.class;
				} else if (cl == Monk.class) {
					cl = Senior.class;
				} else if (cl == Scorpio.class) {
					cl = Acidic.class;
				} else if (cl == Hydra.class) {
					cl = Typhoon.class;
				}
				rotation.set(i, cl);
			}
		}
	}
	*/
}