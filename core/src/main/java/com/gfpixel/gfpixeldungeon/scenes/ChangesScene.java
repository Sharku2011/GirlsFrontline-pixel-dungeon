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
import com.gfpixel.gfpixeldungeon.items.Item;
import com.gfpixel.gfpixeldungeon.items.bags.MagicalHolster;
import com.gfpixel.gfpixeldungeon.items.potions.PotionOfExperience;
import com.gfpixel.gfpixeldungeon.items.rings.RingOfMight;
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


		ChangeInfo changes = new ChangeInfo("v0.5.0", true, "5.0 변경사항 정리중");
    
		changes.hardlight( Window.TITLE_COLOR);
		infos.add(changes);

		changes.addButton( new ChangeButton(new Image(Assets.ELPHELT, 288, 0, 17, 22), Messages.get(this, "텍스트 준비중"),
				"분기점 활성화\n\n" +

						"_-_ 소녀전선x길티기어 분기점 챕터가 활성화 되었습니다.\n\n" +
						"_-_ 기존 스테이지에서 특정 조건을 만족할시 다음 챕터에서 분기점 챕터가 활성화됩니다.\n\n" +
						"_-_ 분기점 챕터를 클리어할시 기존 티어보다 높은 성능을 가진 콜라보 무기를 획득할 수 있습니다. \n\n" +
						"_-_ 분기점 활성화 조건은 공개하지 않겠습니다. 하지만 찾기 쉬울거에요! 첫번째 분기점인 만큼, 초반 챕터에 분기점이 활성화됩니다!"));


		changes = new ChangeInfo(Messages.get(this, "new"), false, null);
		changes.hardlight( Window.TITLE_COLOR );
		infos.add(changes);

		changes.addButton( new ChangeButton(new Gepard(),
				"_-_ 일부 무기가 수정됬습니다.\n\n" +
						"_-_ 신규무기 게파드가 추가됬습니다. 게파드는 무기를 장착하고 있을때 50BMG의 데미지가 크게 올라갑니다.\n\n" +
						"_-_ 소이탄, EMP탄,유탄이 삭제되고 50BMG라는 아이템으로 바뀌었습니다. 삭제된 EMP탄과 소이탄은 솥에서 50BMG와 씨앗을 조합해 만들 수 있습니다.\n\n" +
						"_-_ UMP45와 M16A1의 데미지가 소폭 상승했습니다. M16A1은 추가로 강화 효율과 방어율이 소폭 상승했습니다."));

		changes.addButton( new ChangeButton(Icons.get(Icons.SHPX), "개발 관련",
				"_-_ \"UI, 기타 시스템 변경\n\n" +

						"_-_ UI가 크게 개편됬습니다! 좀더 소녀전선에 가까운 UI를 만들기 위해 노력했습니다.\n\n" +
						"_-_ 투척무기가 개편됬습니다. 많은 투척무기가 추가되었고, 투척무기에 내구도 시스템이 적용됬습니다.\n\n" +
						"_-_ 수중 정찰기가 있는 방에 1칸의 타일을 깔아둬 실수로 들어가 데미지를 입는 경우를 줄였습니다. \n\n" +
						"_-_ 세이브슬롯이 10칸으로 증가했습니다."));

		changes = new ChangeInfo("v0.4.0b3e1", true, "긴급수정");
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
