package jp.ac.hal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		int[][] board = new int[3][3];
		int count = 1;//ターン管理
		int player = 0;
		boolean judge = true;//勝敗管理
		
		//盤面説明
		System.out.println("【○×ゲーム】");
		System.out.println("ーーーーーーー");
		System.out.println("｜１｜２｜３｜");
		System.out.println("ーーーーーーー");
		System.out.println("｜４｜５｜６｜");
		System.out.println("ーーーーーーー");
		System.out.println("｜７｜８｜９｜");
		System.out.println("ーーーーーーー");
		
		game: while(judge) {
			if(count % 2 != 0) {
				System.out.print("Player1:");
				player = 1;
			}else {
				System.out.print("Player2:");
				player = 2;
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String str = br.readLine();
			
			//正規表現処理
			if(!str.matches("[1-9]")){
				System.out.println("半角数字 1～9 で入力してください。");
				continue game;
			}
			
			int input = Integer.parseInt(str);
			
			//奪い禁止処理
			if(board[(input - 1) / 3][(input - 1) % 3] == player){
				System.out.println("空の場所を指定してください。");
				continue game;
			}
			
			//入力値挿入
			board[(input - 1) / 3][(input - 1) % 3] = player;
			
			//盤面出力
			for(int i = 0; i < board.length; i++) {
				System.out.println("ーーーーーーー");
				for(int j = 0; j < board[i].length; j++) {
					System.out.print("｜");
					if(board[i][j] == 0) {
						System.out.print((char)((char)((i * 3 + j + 1) + '0') + 0xFEE0));
					}else if(board[i][j] == 1) {
						System.out.print("○");
					}else {
						System.out.print("×");
					}
				}
				System.out.println("｜");
			}
			System.out.println("ーーーーーーー");
			
			//横縦チェック
			for(int i = 0; i < board.length; i++){
				int yoko_count = 0;
				int tate_count = 0;
				for(int j = 0; j < board[i].length; j++){
					if(board[i][j] == player){
						yoko_count++;
					}
					if(board[j][i] == player){
						tate_count++;
					}
				}
				if(yoko_count == board.length || tate_count == board.length){
					judge = false;
					break;
				}
			}
			
			//斜めチェック
			int naname_shita_count = 0;
			int naname_ue_count = 0;
			for(int i = 0; i < board.length; i++){
				//右斜め下([0,0],[1,1],[2,2])チェック
				if(board[i][i] == player){
					naname_shita_count++;
				}
				//右斜め上([2,0],[1,1],[0,2])チェック
				if(board[board.length - i - 1][i] == player){
					naname_ue_count++;
				}
			}
			if(naname_shita_count == board.length || naname_ue_count == board.length){
				judge = false;
			}
			
			//引き分け
			if(judge && count == 9) {
				System.out.print("引き分け");
				System.exit(0);
			}
			
			count++;
			
		}
		System.out.print("Player" + player + "の勝利！");
		
	}

}
