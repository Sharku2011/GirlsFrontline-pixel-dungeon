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

package com.gfpixel.gfpixeldungeon.scenes;

import com.gfpixel.gfpixeldungeon.Assets;
import com.gfpixel.gfpixeldungeon.Chrome;
import com.gfpixel.gfpixeldungeon.GirlsFrontlinePixelDungeon;
import com.gfpixel.gfpixeldungeon.items.Generator;
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.armor.curses.Bulk;
import com.gfpixel.gfpixeldungeon.items.bags.VelvetPouch;
import com.gfpixel.gfpixeldungeon.items.potions.Potion;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfDisintegration;
import com.gfpixel.gfpixeldungeon.items.weapon.Weapon;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Kriss;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M16;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M9;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Negev;
import com.gfpixel.gfpixeldungeon.levels.traps.CursingTrap;
import com.gfpixel.gfpixeldungeon.plants.Plant;
import com.gfpixel.gfpixeldungeon.plants.Sungrass;
import com.gfpixel.gfpixeldungeon.items.armor.HuntressArmor;
import com.gfpixel.gfpixeldungeon.items.armor.PlateArmor;
import com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfExperience;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfHealing;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfMight;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfSharpshooting;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfElements;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfEvasion;
import com.gfpixel.gfpixeldungeon.items.artifacts.TimekeepersHourglass;
import com.gfpixel.gfpixeldungeon.items.artifacts.TalismanOfForesight;
import com.gfpixel.gfpixeldungeon.items.wands.WandOfTransfusion;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.AK47;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.C96;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Dp;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.G36;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Gepard;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Hk416;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.M99;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Mg42;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.NAGANT;
import com.gfpixel.gfpixeldungeon.items.weapon.melee.Ump45;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.Shuriken;
import com.gfpixel.gfpixeldungeon.items.weapon.missiles.ThrowingKnife;
import com.gfpixel.gfpixeldungeon.messages.Messages;
import com.gfpixel.gfpixeldungeon.sprites.CharSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSprite;
import com.gfpixel.gfpixeldungeon.sprites.ItemSpriteSheet;
import com.gfpixel.gfpixeldungeon.ui.Archs;
import com.gfpixel.gfpixeldungeon.ui.ExitButton;
import com.gfpixel.gfpixeldungeon.ui.Icons;
import com.gfpixel.gfpixeldungeon.ui.RenderedTextMultiline;
import com.gfpixel.gfpixeldungeon.ui.ScrollPane;
import com.gfpixel.gfpixeldungeon.ui.Window;
import com.gfpixel.gfpixeldungeon.windows.WndTitledMessage;
import com.watabou.input.Touchscreen;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.RenderedText;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

//TODO: update this class with relevant info as new versions come out.
public class ChangesScene extends PixelScene {

	private final ArrayList<ChangeInfo> infos = new ArrayList<>();

	@Override
	public void create() {
		super.create();

		int w = Camera.main.width;
		int h = Camera.main.height;

		RenderedText title = PixelScene.renderText( Messages.get(this, "title"), 9 );
		title.hardlight(Window.TITLE_COLOR);
		title.x = (w - title.width()) / 2f;
		title.y = (16 - title.baseLine()) / 2f;
		align(title);
		add(title);

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( Camera.main.width - btnExit.width(), 0 );
		add( btnExit );

		NinePatch panel = Chrome.get(Chrome.Type.TOAST);

		int pw = 135 + panel.marginLeft() + panel.marginRight() - 2;
		int ph = h - 16;

		panel.size( pw, ph );
		panel.x = (w - pw) / 2f;
		panel.y = title.y + title.height();
		align( panel );
		add( panel );

		ScrollPane list = new ScrollPane( new Component() ){

			@Override
			public void onClick(float x, float y) {
				for (ChangeInfo info : infos){
					if (info.onClick( x, y )){
						return;
					}
				}
			}

		};
		add( list );
		
		//**********************
		//       v0.6.5
		//**********************

		ChangeInfo changes = new ChangeInfo("v0.4.7", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M9(),
				"무기 변경.\n\n" +

						"_-_ HK416의 기본무기 제리코가 MP7으로 변경됐습니다. \n\n" +
						"_-_ MP7은 MG계열보단 느리지만 매우 빠른 공격속도와 비교적 강한 공격력, 낮은 명중률을 가지고있습니다."));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"버그 수정\n\n" +

						"_-_ 오타와 맛춤뻡들을 고쳣습미다맛춤뻡을 꼮 지키는 착한 사람이 됍시다\n\n" +
						"_-_ 재규어의 공격이 격발될 때 크래시가 발생하는 버그를 수정하였습니다.\n\n" +
						"_-_ 엘펠트가 자고 있던 중 체력의 절반 이상을 잃게 될 경우, 2페이즈가 아닌 1페이즈로 시작하는 버그를 수정하였습니다. \n\n" +
						"_-_ 엘펠트가 2페이즈에서 돌진과 매그넘웨딩을 발사한 뒤 플레이어를 놓치는 버그를 수정하였습니다. \n\n" +
						"_-_ 엘펠트가 벽에 붙어있는 적에게 돌진할 경우 크래시가 발생하는 버그를 수정하였습니다. \n\n" +
						"_-_ 엘펠트가 자고 있을 때 새틀라이트에게 공격받을 시, 페이즈가 진행되지 않던 버그를 수정하였습니다."));

		changes.addButton( new ChangeButton(new VelvetPouch(),
				"_-_ 아이템 조정.\n\n" +
						"_-_ 다목적 쵸크가 고급 장비 교정권으로 변경되었습니다. 무기와 외골격에 모두 사용할 수 있으며 외골격에 사용 시 방어력과 회피력 중 하나를 희생하여 나머지를 강화시킵니다.\n\n" +
						"_-_ 장비 교정권의 이름이 다른 아이템과의 혼동을 피하기 위해 외골격 교정권으로 변경되었습니다.\n\n" +
						"_-_ 씨앗 주머니가 벨벳 주머니로 변경되었습니다. 씨앗 이외에도 외골격 교정권과 고급 장비 교정권, 특성 장비 교정권, 고속 수복 계약을 추가로 수납할 수 있습니다."));

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ \"시스템 변경\n\n" +

						"_-_ 던전의 깊이에 따라 비밀문과 숨겨진 함정을 의도치 않게 찾을 확률이 조정되었습니다.\n\n\n" +
						"_-_ 필드에서 마비 물약이 식별된 상태로 드랍되던 버그를 수정하였습니다.\n\n\n" +
						"_-_ 전술인형 스프링필드를 잡을 경우 드랍되는 마비 물약이 자동으로 식별되도록 변경되었습니다."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 16, 16), "악랄해짐",
				"일부 몹 강화.\n\n" +
						"_-_ 게이저의 패턴에 돌진이 추가되었습니다.\n\n" +
						"_-_ 네메움의 체력이 80으로 상향조치 됐으며, 광선 데미지도 최대 50으로 상향됐습니다. 대신 사거리가 2칸으로 줄어들게 됬습니다.\n\n" +
						"_-_ 만티코어의 공격력이 크게 상향됐습니다."));

		changes.addButton( new ChangeButton(new M16(),
				"무기 조정.\n\n" +
						"_-_ M16A1이 다시 기습공격을 할 수 있으며, 데미지가 소폭 상승했습니다.\n\n" +
						"_-_ 런쳐 계열(50구경 강화무기) 무기들의 느린 공격속도가 사라져 평균적인 공격속도를 가지게 됐습니다.\n\n" +
						"_-_ MG계열의 명중률이 소폭 상향됐지만 기습 데미지는 더 떨어졌습니다..\n\n" +
						"_-_ 미스 트라비아에의 데미지 흡수를 제외한 모든 부가 효과가 삭제됐으며, 대신 무기 자체 스킬을 사용할 수 있게 됐습니다."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Kriss(),
				"벡터가 전반적으로 하향되었습니다.\n\n" +
						"_-_ 기습공격이 불가능해졌습니다.\n\n" +
						"_-_ 명중 시 적용되던 시간 증폭 버프의 유지 시간이 감소되었습니다.\n\n" +
						"_-_ 명중률이 50% 감소하였습니다."));

		changes.addButton( new ChangeButton(new Negev(),
				"_-_ MG계열 하향.\n\n" +
						"_-_ MG계열(공격속도가 기본 5회 이상인 무기) 무기들의 명중률과 기습공격이 조정됬습니다.\n\n" +
						"_-_ 최대 40%의 명중률 패널티를 가지며, 공격력은 그대로지만 기습공격시 60%의 피해만 입힐 수 있게 됩니다."));


		changes = new ChangeInfo("v0.4.6", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);


		changes.addButton( new ChangeButton(new Image(Assets.ELPHELT, 288, 0, 18, 19), "분기점",
				"_-_ 1번 분기점 활성화. \n\n" +
						"_-_ 소녀전선&길티기어&블레이블루 분기점 챕터가 활성화 되었습니다.\n\n" +
						"_-_ 기존 스테이지에서 특정 조건을 만족할시 다음 챕터에서 분기점 챕터가 활성화됩니다.\n\n" +
						"_-_ 분기점 챕터를 클리어할시 기존 티어보다 높은 성능을 가진 콜라보 무기를 획득할 수 있습니다. \n\n" +
						"_-_ 분기점 활성화 조건은 공개하지 않겠습니다. 하지만 찾기 쉬울거에요! \n\n" +
						"_-_ 현재 보스 Ai 버그가 해결되지 않은 상태입니다. 다음 버전에서 수정될 예정이니 양해바랍니다."));


		changes.addButton( new ChangeButton(new Image(Assets.GAGER, 0, 0, 24, 18), "신규 보스",
				"_-_ 3챕터 보스 변경. \n\n" +
						"_-_ 3챕터 보스가 만티코어가 필드 몬스터로 바뀐 관계로 새로운 보스가 추가 됐습니다.\n\n" +
						"_-_ 게이저는 기존의 만티코어와 다른 공격패턴을 가지고있으며, 아직 미완성이므로 추후 새로운 패턴이 추가 될 예정입니다."));

		changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.ARMOR_CLOTH, new Bulk().glowing()), "신규 저주",//이부분을 저주함정이나 저주받은 외골격 이미지로 바꿔주세요
				"_-_ 여러 종류의 저주가 추가됬습니다.\n\n" +
						"_-_ 친화적인 저주 - 무기 저주. 타격시 때때로 적과 자신을 동시에 교란시킨다.\n\n" +
						"_-_ 탄성의 저주 - 무기 저주. 피해량이 0이 되는 대신, 맞은 적을 튕겨낸다.\n\n" +
						"_-_ 육중함의 저주 - 방어구 저주. 문을 통과하는 데에 걸리는 턴 수를 늘린다.\n\n" +
						"_-_ 과다성장의 저주 - 방어구 저주. 피격시 때때로 임의의 씨앗 효과가 발휘된다."));

		changes.addButton( new ChangeButton(new WandOfDisintegration(),
				"_-_ 장갑 속성이 정의됬습니다.\n\n" +
						"_-_ 장갑 속성은 철갑탄에 3배 피해를 입습니다.\n\n" +
						"_-_ 장갑 속성은 강력한 공격과 높은 체력을 가지고 있지만 대체로 느린 이동속도를 가지고 있으며, 방어력이 낮거나 없기 때문에 공격이 더 잘 명중하며 높은 데미지를 입힐 수 있습니다."));

		changes.addButton( new ChangeButton(new Gepard(),
				"_-_ 일부 무기가 수정됬습니다.\n\n" +
						"_-_ 신규무기 게파드와 SRS가 추가됬습니다. 게파드는 무기를 장착하고 있을때 50구경의 데미지가 크게 올라갑니다.\n\n" +
						"_-_ 소이탄, EMP탄,유탄이 삭제되고 50구경라는 아이템으로 바뀌었습니다. 삭제된 EMP탄과 소이탄은 솥에서 50구경과 씨앗을 조합해 만들 수 있습니다.\n\n" +
						"_-_ PG 가스탄이 붕괴액으로 바뀌었습니다. 기존의 독가스 대신 붕괴가스를 발사하며, 붕괴가스에 노출되면 산성효과와 비슷한 피해를 입게됩니다.\n\n" +
						"_-_ UMP45와 M16A1의 데미지가 소폭 상승했습니다. M16A1은 기습불가와 느린 공격속도가 삭제됐고 추가로 강화 효율과 방어율이 소폭 상승했습니다."));

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ \"시스템 변경\n\n" +

						"_-_ UI가 크게 개편됐습니다! 좀더 소녀전선에 가까운 UI를 만들기 위해 노력했습니다.\n\n" +
						"_-_ 힘 포션이 드랍되지 않는 도전모드가 추가됬습니다.\n\n" +
						"_-_  새로운 뱃지들이 추가됬습니다.\n\n" +
						"_-_ 투척무기가 개편됬습니다. 많은 투척무기가 추가되었고, 투척무기에 내구도 시스템이 적용됬습니다.\n\n" +
						"_-_ 수중 정찰기가 있는 방에 1칸의 타일을 깔아둬 실수로 들어가 데미지를 입는 경우를 줄였습니다. \n\n" +
						"_-_ 세이브슬롯이 10칸으로 증가했습니다."));

		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new PlateArmor(),
				"_-_ 장비 특성들이 바뀌었습니다.\n\n" +
						"_-_ 행운 특성이 2배 피해를 가할 확률이 60%에서 50%로 감소한 대신 0배 피해를 가한 경우, 다음 공격이 2배 피해를 가할 확률이 증가합니다.\n\n" +
						"_-_ 흐릿함- 방어도 패널티가 제거되고 흐릿함 효과가 감소\n\n" +
						"_-_ 속박- 제공되는 자연의 갑옷수치가 증가, 강화수치가 높을수록 속박시간 감소\n\n" +
						"_-_ 전위- 자가피해 삭제 및 총탄 충전량 증가\n\n" +
						"_-_ 헤비아머- 회피가 0으로 되는대신 회피에 비례해 방어력이 증가함\n\n" +
						"_-_ 신속- 방어력 패널티 제거, 주변에 적이 없을때만 이속증가 부여\n\n" +
						"_-_ 점성- 나올확률이 증가하고 헤비아머가 나올 확률이 감소"));

		changes.addButton( new ChangeButton(new MagicalHolster(),
				"_-_ IOP 대용량 탄창에 투척무기를 보관할수있습니다\n\n" +
						"_-_ 탄창에 보관된 투척무기는 20%의 내구도를 추가로 가지게 됩니다."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_RUBY, null), new RingOfSharpshooting().trueName(),
				"저격의 도트사이트 변경\n\n" +
						"_-_ 투척 공격에 대한 추가 명중률 제거\n\n" +
						"_-_ 투척 무기가 소모되지 않을 확률 제거, 대신 원거리 무기의 내구도를 증가시킴\n\n" +
						"_-_ 투척 무기의 피해량을 강화 수치에 비례하여 증가시키는 효과 추가"));

		changes.addButton( new ChangeButton(new Image(Assets.NEMEUM, 0, 0, 16, 16), "변경",
				"기계형 몬스터가 개편됬습니다.\n\n" +
						"_-_ 아래 설명에 나오는 몬스터에겐 전부 장갑 속성이 부여됩니다.\n\n" +
						"_-_ 네메움은 이제 챕터 3 구간에 등장하며, 짧은 시간동안 장전한 뒤 지형과 외골격의 방어율을 무시하는 붕괴광선을 발사합니다.\n\n" +
						"_-_ 만티코어는 이제 챕터 4 구간 필드 몬스터로 등장합니다. 만티코어는 방어력이 없고 조금 느린 대신, 높은 체력을 가지고 있으며 지정된 피해량 이상의 피해를 입힐수 없습니다.\n\n" +
						"_-_ 주피터의 장전중 데미지 경감이 삭제됐고 체력은 기존의 두배인 110으로 조정됬습니다.\n\n" +
						"_-_ 양산형 티폰의 체력이 3500으로 늘어났고, 이동속도도 20% 빨라졌으며 공중에 떠다니기 때문에 함정을 밟지 않습니다. 또한 마비와 공포에 면역이며 즉사, 독가스, 흡혈, 자폭의 디스크에 내성을 갖고있습니다."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.HUNTRESS, 0, 34, 13, 17), "상향",
				"HK416 상향.\n\n" +
						"_-_모든 투척무기 내구도가  50% 추가됨\n\n" +
						"_-_M79 유탄발사기의 매커니즘이 바뀌어 적이 아니라 벽 등을 맞춰도 폭발해 스플래시 데미지를 줄 수 있도록 변경되었습니다.\n\n" +
						"IOP 탄창이 제공하는 투척무기 내구도 20% 증가와 기본 능력이 곱연산으로 중첩되 80%의 추가 내구도를 가지게 됩니다."));

		changes.addButton( new ChangeButton( new Image(Assets.MAGE, 0, 34, 13, 17), "리워크",
				"G11 외골격 리워크.\n\n" +
						"_-_ G11의 외골격 스킬이 액티브에서 버프로 바뀌었습니다. 반드시 체력이 100% 일때만 사용하십시오.\n\n" +
						"_-_ G11의 외골격 스킬이 주변의 적을 화상 상태로 만드는 쓸모없는 능력에서 가속이라는 버프를 받게 됩니다.\n\n" +
						"가속은 G11의 체력을 거의 죽음에 가까운 90%를 소모하지만, 4턴동안 시간과 관련된 모든 능력이 증폭됩니다. 공격속도, 이동속도, 가속을 포함한 모든 버프 지속 시간까지 일시적으로 증가해, 짧은 시간동안 강력하고 다양한 전술을 펼칠 수 있습니다."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_OPAL, null), new RingOfElements().trueName(),
				"원소의 도트사이트 상향\n\n" +
						"_-_ 이전보다 더 많은 원소/원거리 효과에 대응하여 발동\n\n" +
						"_-_ 높은 강화 수치에서 약화 효과의 지속 시간과 피해량이 기존보다 상당히 감소\n\n" +
						"_-_이제 원소/마법 피해에 저항할 확률 대신 원소/마법 피해를 강화 레벨에 비례하여 감소시키는 효과를 부여"));

		changes.addButton( new ChangeButton(new TimekeepersHourglass (),
				"_-_ 회중시계 상향.\n\n" +
						"_-_ 충전수가 절반으로 감소\n\n" +
						"_-_ 충전수 1당 주변의 시간을 멈추는 정도가 2배로 증가\n\n" +
						"_-_ 낮은 강화 레벨에서 충전 속도가 증가, +10에 도달시 이전과 동일"));

		changes.addButton( new ChangeButton(new TalismanOfForesight(),
				"_-_ 안경이 갓경됨.\n\n" +
						"_-_ 충전 속도가 +0에서 20% 증가, 강화 레벨에 따라 기존에 비해 충전 속도가 더 많이 증가하며 최종적으로 +10에서 50% 증가\n\n" +
						"_-_ 함정을 찾아내는 것을 통해 얻는 보너스 충전치는 변경 없음"));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.WARRIOR, 0, 34, 15, 17), "너프",
				"투신 변경 및 하향\n\n" +
						"_-_ 격노(죽음 유예)하기 위한 레벨이 2에서 3으로 증가\n\n" +
						"_-_ 물리 피해를 입을때마다 분노 버프가 쌓이며 버프에 비례해 자신의 물리공격력이 50%까지 증가, 버프는 시간이 지날수록 줄어들지만 체력이 낮을수록 느리게 감소\n\n" +
						"_-_ 격노하기 위해선 분노 버프가 100%이여야 하며 이 상태에서 추가 피해량이 50%로 감소함.\n\n" +
						"_-_ 격노후 회복중엔 분노 버프가 쌓이지 않음."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_EMERALD, null), new RingOfEvasion().trueName(),
				"회피의 도트사이트 하향\n\n" +
						"_-_ 더 이상 적들에게 감지될 확률을 낮추지 않음\n\n" +
						"_-_정찰병이나 신속함의 특성의 효과와 중첩되는 정도 감소 -계산식이 곱연산에서 합연산으로 바뀜."));

		changes.addButton( new ChangeButton(new HuntressArmor(),
				"_-_ UMP9 & HK416의 외골격 하향.\n\n" +
						"_-_ UMP9 - 연막탄 범위가 8칸으로 제한되며, 효과가 벽을 관통하지 않도록 변경\n\n" +
						"_-_ HK416 - 살상류탄의 범위가 12칸으로 제한됨"));

		changes.addButton( new ChangeButton(new Negev(),
				"_-_ MG계열 하향.\n\n" +
						"_-_ MG계열(공격속도가 기본 5회 이상인 무기) 무기들의 명중률과 기습공격이 조정됬습니다.\n\n" +
						"_-_ 최대 40%의 명중률 패널티를 가지며, 공격력은 그대로지만 기습공격시 60%의 피해만 입힐 수 있게 됩니다."));
        changes.addButton( new ChangeButton(new ItemSprite(ItemSpriteSheet.POTION_MAGENTA, null), new PotionOfHealing().trueName(),
				"_-_ 수복포션 드랍 하향.\n\n" +
						"_-_ 수복물약 드랍 몹에게 수복물약을 얻은 경우 이후 같은 몹에게 수복물약을 얻을 확률이 감소함"));

		changes.addButton( new ChangeButton(new Sungrass.Seed(),
				"_-_ 태양초 & 뱀뿌리 - 하향.\n\n" +
						"_-_ 태양초의 회복 속도 대폭 감소, 레벨이 높을 수록 더 많이 감소하여 최대 기존의 40% 속도까지 감소합니다.\n\n" +
						"_-_ 태양초의 회복을 받는 도중 피격당해도 회복량이 줄지 않는다.\n\n" +
						"_-_ 태양초의 회복을 받는 도중 체력이 가득 차도 효과가 바로 끝나지 않는다.\n\n" +
						"_-_ 뱀뿌리의 피해 흡수 비율이 기존의 50%에서 층 수에 비례하도록 변경"));

		changes = new ChangeInfo("v0.4.0b3e1", true, "긴급 수정");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"치명적 버그 수정\n\n" +

						"_-_ 17층 이후 확률적으로 게임이 강제 종료되는 동시에 세이브가 깨지는 버그를 수정했습니다.\n\n" +
						"_-_ 주피터와 하이드라, 티폰 양산형의 레이저 차징중 데미지 감소가 5배에서 2배로 감소했습니다. 대신 주피터와 하이드라의 체력을 소폭 상향했습니다.\n\n" +
						"_-_ UMP45의 기본무기 데미지가 2 상승했고 기습 추가데미지와 낮은 명중률, 긴 사거리가 삭제된 대신 낮은 피해흡수를 가지게됩니다. \n\n" +
						"_-_ UMP9의 기본무기 데미지가 2 너프됬습니다. 이미 충분히 높은 기습데미지를 가지고있기 때문에 너프하는게 당연하다 생각했습니다. \n\n" +
						"_-_ 6티어 무기 드랍확률과 성능을 재조정했습니다. 예상 외로 너무 높은 드랍률을 보였기 때문에 5티어와 비슷한 수준으로 조정했습니다."));


		changes = new ChangeInfo("v0.4.0b3", true, "");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Dp(),
				"2종의 무기가 추가되었습니다!\n\n" +
						"_-_ 1티어 히든무기와 5티어에 새로운 무기가 추가됬습니다!"));

		changes.addButton( new ChangeButton(new Image(Assets.KING, 0, 0, 17, 18), "추가",
				"새로운 보스와 레어 몬스터, 새로운 테마가 추가됬습니다.\n\n" +
						"_-_ 25층의 보스를 조금 수정했고, 30층 구간의 레어 몬스터와 25층의 새로운 테마가 추가됬습니다. "));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"_-_ 텍스트 출력 문제를 해결했습니다." ));


		changes.addButton( new ChangeButton(new Image(Assets.GHOST, 0, 0, 14, 16), "개발진 교체",
				"_-_ 기존 개발자 Kirsi 님이 새로운 개발자 Sharku 님으로 바뀌었습니다. 기초 개발 수고하셨습니다. Kirsi 님! " ));


		changes.addButton( new ChangeButton(new Ump45(),
				"_-_ 일부 무기가 수정됬습니다.\n\n" +
						"_-_ UMP45의 기본무기가 바뀌었습니다. 직접 확인해보세요!\n\n" +
						"_-_ 더블베럴 소드오프가 M16A1으로 교체됬습니다. M16A1은 방탄판과 비슷한 효과를 가지며 먼 거리를 공격할수있지만 조금 느리고 명중률이 낮습니다."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M99(),
				"M99 재조정.\n\n" +
						"M99의 높은 파괴력은 충분히 매력적이지만, 기습불가와 낮은 명중률은 너무나 가혹한 디메리트였습니다. 이 무기 하향의 이유는 암살자와의 시너지가 너무 크다는 이유였으므로,\n 기습불가는 유지하되 명중률을 크게 올려 암살자의 암살 시너지를 받지 못하게했습니다."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.CYCLOPS, 0, 0, 12, 16), "너프",
				"일부 적의 체력을 낮췄습니다.\n\n" +
						"_-_ 하이드라의 체력이 360에서 190으로 너프됬습니다.\n\n" +
						"_-_ 방패병의 체력이 늘어났지만, 이동속도와 공격력, 방어력이 감소했습니다."));

		changes = new ChangeInfo("v0.4.0b2e1", true, "긴급 수정");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton( new C96(),
				"히든무기 긴급 조정\n\n" +

						"_-_ 필드드랍되는 히든무기 2종을 정규 무기로 만들기로 했습니다. 하지만 아직 2가지 히든무기가 남아있으며, 이 2개의 무기는 일반적인 방법으로 얻을 수 없습니다.\n\n" +
						"_-_ 정규화 된 무기들은 C96, G36 이 두가지이며, 데미지를 크게 깎고 무기의 특성은 일부 유지했습니다.\n\n" +
						"_-_ G36은 3티어에서 2티어로 내려갔고, 긴 사거리와 빠른 공격속도를 삭제했습니다. 하지만 기존만큼은 아니지만 높은 강화효율과 살짝 빠른 공격속도를 가집니다.\n\n" +
						"_-_ C96은 2티어에서 3티어로 올라갔고, 강화효율이 삭제되고 기본 데미지가 크게 너프됬지만, 긴 사거리와 기습 추가데미지가 추가됬습니다. "));

		changes = new ChangeInfo("v0.4.0b2", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new G36(),
				"2종의 히든무기가 추가되었습니다!\n\n" +
						"_-_ 히든 무기들은 같은 티어 무기들보다 우월한 성능을 가지며, 평범한 방법으로는 얻을 수 없습니다. 행운을 빌어요! 전 안가르쳐줄거에요!"));

		changes.addButton( new ChangeButton(new ThrowingKnife(),
				"몇몇 투척무기가 불안정하게 추가됬습니다.\n\n" +
						"_-_ 설명대로 작동하지 않을 가능성이 높습니다. 미안해요! 전 어디까지나 코딩 조무사에요!"));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"_-_ 텍스트 문제를 해결했습니다." ));


		changes.addButton( new ChangeButton(new MagicalHolster(),
				"_-_ 초반 난이도를 낮췄습니다!\n\n" +
						"_-_ 4명의 캐릭터 모두 기본 아이템을 두가지를 추가했습니다.\n\n" +
						"_-_ UMP45는 기본으로 물약 보관함과 수복포션을 가지고 시작하며, UMP9는 디스크 보관함과 투명화 포션을 가지고 시작합니다.\n\n" +
						"_-_ 그리고 G11은 대용량 탄창과 고속 장전의 디스크를, HK416은 씨앗 주머니를 가지고 시작합니다."));


		changes.addButton( new ChangeButton(new NAGANT(),
				"_-_ 6티어 필드드랍이 확인됬습니다!\n\n" +
						"_-_ 6티어의 필드드랍이 확인되어, 드랍률을 조정헀습니다.\n\n" +
						"_-_ 6티어는 5티어보다 드랍될 확률이 조금 높으며, 동시에 힘의 도트사이트 드랍확률도 조금 높혔습니다. 이 패치내용은 추후 수정될수도 있습니다."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton( new Image(Assets.HUNTRESS, 0, 34, 13, 17), "유탄수 상향",
				"유탄수의 유탄 배율을 높혔습니다.\n\n" +
						"좀더 유탄을 통상전에서 쓰기 편하도록 기존 충전 배율을 7%에서 10%로 높혔습니다."));

		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.GOLIATH, 0, 0, 12, 19), "너프",
				"일부 적의 체력을 낮췄습니다.\n\n" +
						"_-_ 기병의 체력을 30으로 조정했습니다. 높은 회피율과 공격력은 그대로지만, 한번의 명중으로 즉사할 체력으로 조정했습니다.\n\n" +
						"_-_ 주피터의 장전시간이 1턴 증가했으며, 우산에 걸릴 확률을 크게 높혔습니다. 하지만 얻는 경험치는 1로 줄어들었습니다.\n\n" +
						"_-_ 하이드라와 씨클롭스의 체력을 40씩 낮춰 각각 360, 120으로 조정됬습니다."));

		//**********************
		//       v0.4.X
		//**********************

		changes = new ChangeInfo("v0.4.0b1", true, "");
		changes.hardlight(Window.TITLE_COLOR);
		infos.add(changes);

		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton( Icons.get(Icons.DEPTH), "던전 개편!",
				"던전 층이 늘어나고 스토리 진행방식을 바꿨습니다!\n\n" +
						"_-_ 새로운 층 23~31층이 추가됬습니다.\n\n" +
						"_-_ 새로운 몬스터 6종이 추가됬습니다.\n\n" +
						"_-_ 스토리 진행방식을 대화 형식으로 바꿔 좀더 스토리에 몰입할수있게 만들었습니다.\n\n" +
						"계속 업데이트할 예정입니다. 몬스터의 능력치 조정이나 아이디어는 트위터나 디씨 배포글에 말해주시면 감사하겠습니다."));

		changes.addButton( new ChangeButton( new Image(Assets.HUNTRESS, 0, 34, 13, 17), "HK416 세부 전직 개편!",
				"HK416의 플레이 스타일이 바뀌었습니다!\n\n" +
						"기존의 코만도가 유탄수로 변경 됬습니다.\n" +
						"_-_ HK416은 M79 유탄발사기를 가지고 시작합니다. \n" +
						"_-_ 유탄수는 자신의 도끼로 적을 타격할 때마다 유탄에 데미지 배율이 추가됩니다.\n\n" +
						"자세한 사항은 디시인사이드의 설명글을 참조해주세요."));

		changes.addButton( new ChangeButton(new Mg42(),
				"_-_ 4티어, 6티어 무기가 추가됬습니다!\n\n" +
						"_-_ 개발중 현재 필드 드랍은 확인되지 않았습니다.\n\n" +
						"_-_ 4, 6티어 무기의 필드 드랍은 확인되지 않았으며, 입수방법은 ST-AR15의 무기로 얻는 것 한가지 뿐입니다.\n\n" +
						"_-_ 만약 6티어 무기를 입수했다면 입수 경로를 제 트위터로 알려주시길 바랍니다.\n\n" +
						"_-_ 트위터 아이디는 @_NamSek입니다. 유저 여러분들의 연락을 기다리겠습니다. 감사합니다."));


		changes = new ChangeInfo(Messages.get(this, "changes"), false, null);
		changes.hardlight( CharSprite.WARNING );
		infos.add(changes);

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"_-_ 많은 아이템들의 텍스쳐가 수정됬습니다!\n\n" +
						"_-_ -기존 도트 사이트들의 텍스쳐 퀄리티를 올리고, 이오텍, 레드닷, 에임포인트 총 3가지 텍스쳐로 바꿨습니다.\n\n" +
						"_-_ -주문서가 마이크로 칩 형태와 설명으로 바뀌었습니다.\n\n" +
						"_-_ 일부 무기, 소비, 유물 아이템들의 텍스쳐가 변경됬습니다. 자세한 사항은 배포 사이트의 패치노트를 참고해주세요."));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.POTION_GOLDEN, null), new PotionOfExperience().trueName(),
				"_-_ 플레이어 레벨 제한이 40으로 확장됬습니다!\n\n" +
						"_-_ 그리고 행운 특성에서 0데미지가 나올 확률이 10% 낮아졌습니다!"));

		changes.addButton( new ChangeButton(new Hk416(),
				"_-_ 많은 무기들의 특성이 조정됬습니다.\n\n" +
						"_-_ 산탄총 계열 무기들의 명중률이 15% 추가됬습니다.\n\n" +
						"_-_ 기존의 죽창이였던 스프링필드의 데미지가 2배로 오르고, 사거리와 반동이 50칸,5턴으로 수정됬습니다.\n\n" +
						"_-_ Kar98k, SVD, S.SASS의 고명중 특성이 삭제되고 2칸의 사거리와 +50%의 공격속도가 추가됬습니다.\n\n" +
						"_-_ 기존의 마이크로 우지였던 네게브의 공격속도를 1턴 5회 타격으로 수정하고 데미지가 1티어보다 낮게 조정됬습니다."));

		changes = new ChangeInfo(Messages.get(this, "buffs"), false, null);
		changes.hardlight( CharSprite.POSITIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new AK47(),
				"AK-47의 사정거리 1칸 증가\n\n" +
						"_-_ AK-47은 충분히 강력하지만 낮은 명중률의 패널티가 발목을 붙잡는 무기였습니다. 그래서 AK-47에게 1칸의 선공기회를 부여해 무기 선택의 여지를 높히기로 했습니다."));

		changes.addButton( new ChangeButton(new Shuriken(),
				"투척 무기 데미지 조정\n\n" +
						"_-_ 그동안 유탄과 회전류탄은 낮은 최소 데미지 때문에 의미있는 타격을 줄 수 없었던적이 많았습니다. 그래서 각각 최소 데미지를 조금씩 올려 좀더 유효한 타격을 입힐 수 있도록 조정했습니다."));

		changes.addButton( new ChangeButton(new WandOfTransfusion(),
				"드레인 탄환의 상성에 맞는 적을 추가했습니다!\n\n" +
						"_-_ 드레인 탄환은 그동안 상급 보급패키지에서 나올 때 마다 유저들의 인상을 찌푸리는 아이템이였습니다. 저는 이 탄환의 용도를 조금 늘리기 위해 엘리트 적들에게 오직 드레인 탄환에만 반응하는 약점을 부여해 드레인 탄환을 간접 상향시켰습니다."));
		changes = new ChangeInfo(Messages.get(this, "nerfs"), false, null);
		changes.hardlight( CharSprite.NEGATIVE );
		infos.add(changes);

		changes.addButton( new ChangeButton(new M99(),
				"M99의 느린 공격 딜레이가 삭제되고 명중률 10% 감소와 기습 불가 패널티가 생겼습니다.:\n\n" +
						"_-_ M99는 패널티에 비해 강점이 너무나도 강력했습니다. M99의 느린 공격속도는 삭제되지만, 기습 불가와 -10% 명중률이라는 패널티를 넣기로 했습니다."));

		//**********************
		//       v0.3.X
		//**********************

		changes = new ChangeInfo( "v0.3.0", true, "더 많은 정보가 곧 여기에 추가됩니다.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ 2017-10-26 22:11:55. 셰터드 픽셀던전 v0.6.2 기반으로 개발 시작"));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"일부 아이템들의 텍스쳐가 수정됬습니다." ));

		//**********************
		//       v0.2.X
		//**********************

		changes = new ChangeInfo( "v0.2.0", true, "더 많은 정보가 곧 여기에 추가됩니다.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.SPINNER, 144, 0, 16, 16), Messages.get(this, "bugfixes"),
				"Fixed:\n" +
						"_-_ 셰터드 픽셀던전과 패키지를 독립시켜 따로 설치가 가능하게 되었습니다." ));

		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"일부 아이템들의 텍스쳐가 수정됬습니다." ));

		//**********************
		//       v0.1.X
		//**********************

		changes = new ChangeInfo( "v0.1.0", true, "더 많은 정보가 곧 여기에 추가됩니다.");
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ \"2017-09-24 21:22:09. 셰터드 픽셀던전 v0.6.1 기반으로 개발 시작\n\n" +
						"_-_ 파이팅! 흥해라 소픽던!"));


		changes.addButton( new ChangeButton( new ItemSprite(ItemSpriteSheet.RING_GARNET, null), new RingOfMight().trueName(),
				"일부 아이템들의 텍스쳐가 수정됬습니다." ));
		Component content = list.content();
		content.clear();

		float posY = 0;
		float nextPosY = 0;
		boolean second =false;
		for (ChangeInfo info : infos){
			if (info.major) {
				posY = nextPosY;
				second = false;
				info.setRect(0, posY, panel.innerWidth(), 0);
				content.add(info);
				posY = nextPosY = info.bottom();
			} else {
				if (!second){
					second = true;
					info.setRect(0, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = info.bottom();
				} else {
					second = false;
					info.setRect(panel.innerWidth()/2f, posY, panel.innerWidth()/2f, 0);
					content.add(info);
					nextPosY = Math.max(info.bottom(), nextPosY);
					posY = nextPosY;
				}
			}
		}


		content.setSize( panel.innerWidth(), (int)Math.ceil(posY) );

		list.setRect(
				panel.x + panel.marginLeft(),
				panel.y + panel.marginTop() - 1,
				panel.innerWidth(),
				panel.innerHeight() + 2);
		list.scrollTo(0, 0);

		Archs archs = new Archs();
		archs.setSize( Camera.main.width, Camera.main.height );
		addToBack( archs );

		fadeIn();
	}

	@Override
	protected void onBackPressed() {
		GirlsFrontlinePixelDungeon.switchNoFade(TitleScene.class);
	}

	private static class ChangeInfo extends Component {

		protected ColorBlock line;

		private RenderedText title;
		private boolean major;

		private RenderedTextMultiline text;

		private ArrayList<ChangeButton> buttons = new ArrayList<>();

		public ChangeInfo( String title, boolean majorTitle, String text){
			super();
			
			if (majorTitle){
				this.title = PixelScene.renderText( title, 9 );
				line = new ColorBlock( 1, 1, 0xFF222222);
				add(line);
			} else {
				this.title = PixelScene.renderText( title, 6 );
				line = new ColorBlock( 1, 1, 0xFF333333);
				add(line);
			}
			major = majorTitle;

			add(this.title);

			if (text != null && !text.equals("")){
				this.text = PixelScene.renderMultiline(text, 6);
				add(this.text);
			}

		}

		public void hardlight( int color ){
			title.hardlight( color );
		}

		public void addButton( ChangeButton button ){
			buttons.add(button);
			add(button);

			button.setSize(16, 16);
			layout();
		}

		public boolean onClick( float x, float y ){
			for( ChangeButton button : buttons){
				if (button.inside(x, y)){
					button.onClick();
					return true;
				}
			}
			return false;
		}

		@Override
		protected void layout() {
			float posY = this.y + 2;
			if (major) posY += 2;

			title.x = x + (width - title.width()) / 2f;
			title.y = posY;
			PixelScene.align( title );
			posY += title.baseLine() + 2;

			if (text != null) {
				text.maxWidth((int) width());
				text.setPos(x, posY);
				posY += text.height();
			}

			float posX = x;
			float tallest = 0;
			for (ChangeButton change : buttons){

				if (posX + change.width() >= right()){
					posX = x;
					posY += tallest;
					tallest = 0;
				}

				//centers
				if (posX == x){
					float offset = width;
					for (ChangeButton b : buttons){
						offset -= b.width();
						if (offset <= 0){
							offset += b.width();
							break;
						}
					}
					posX += offset / 2f;
				}

				change.setPos(posX, posY);
				posX += change.width();
				if (tallest < change.height()){
					tallest = change.height();
				}
			}
			posY += tallest + 2;

			height = posY - this.y;
			
			if (major) {
				line.size(width(), 1);
				line.x = x;
				line.y = y+2;
			} else if (x == 0){
				line.size(1, height());
				line.x = width;
				line.y = y;
			} else {
				line.size(1, height());
				line.x = x;
				line.y = y;
			}
		}
	}

	//not actually a button, but functions as one.
	private static class ChangeButton extends Component {

		protected Image icon;
		protected String title;
		protected String message;

		public ChangeButton( Image icon, String title, String message){
			super();
			
			this.icon = icon;
			add(this.icon);

			this.title = Messages.titleCase(title);
			this.message = message;

			layout();
		}

		public ChangeButton( Item item, String message ){
			this( new ItemSprite(item), item.name(), message);
		}

		protected void onClick() {
			GirlsFrontlinePixelDungeon.scene().add(new ChangesWindow(new Image(icon), title, message));
		}

		@Override
		protected void layout() {
			super.layout();

			icon.x = x + (width - icon.width) / 2f;
			icon.y = y + (height - icon.height) / 2f;
			PixelScene.align(icon);
		}
	}
	
	private static class ChangesWindow extends WndTitledMessage {
	
		public ChangesWindow( Image icon, String title, String message ) {
			super( icon, title, message);
			
			add( new TouchArea( chrome ) {
				@Override
				protected void onClick( Touchscreen.Touch touch ) {
					hide();
				}
			} );
			
		}
		
	}
}
