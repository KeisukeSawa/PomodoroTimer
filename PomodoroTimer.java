
import java.nio.file.*;
import java.util.Optional;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.media.*;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Tooltip;

public class PomodoroTimer extends Application {

	TextField setminutes;
	TextField setseconds;  // 分、秒を設定するTextFieldのインスタンス生成
	
	Label minutes = new Label("分");
	Label seconds = new Label("秒");
	Label pomoboro = new Label("ポモドーロタイマー");
	Label label1 = new Label("0");
	Label ato = new Label("あと"); 
	Label tyuui = new Label("Shiftキーを押すと残り時間が分かります");//各種のLabelのインスタンス生成
	
	
	Button start = new Button("スタート");
	Button exit = new Button("終了");
	Button restart = new Button("続ける"); 
	Button cheakalltime = new Button("これまでの学習時間");//各種のボタンのインスタンス生成

	ImageView toptomato = new ImageView("toptomato.png"); // トマトの画像を設定し、ImageViewをインスタンス生成
		
	VBox root = new VBox(); //各種VBoxのインスタンス生成
	
	int x1=0,x2=0,x=0,y=0,flag=0; // x1:設定した分、x2:設定した秒、x：設定した時間の合計(秒)、y：経過時間、flag：正しい値が入力されたか

	Path path = Paths.get("hatodokei.mp3");
	String onsei = path.toUri().toString();
	Media media = new Media(onsei);
	MediaPlayer player = new MediaPlayer(media); 
	MediaView view = new MediaView(); // 音声の設定

	AnchorPane puttime = new AnchorPane(); // 各種AnchorPaneのインスタンス生成
	
	int alltime=0; // 今までのタイマー稼動時間の合計
	
	int nowtime = 0; //現在の経過時間
	
	@Override
	public void start(Stage stage) {
		
		//　各種アンカーペインのインスタンス生成(startメソッドの中でなければいけない)
		AnchorPane pomoborolabel = new AnchorPane(); // 画面上部のタイトル
	    AnchorPane settime = new AnchorPane();  // スタート画面(トマトとトマトの中)
	    puttime = new AnchorPane();
		
		VBox root = new VBox(); //各種VBoxのインスタンス生成
		
		flag = 0; // flagの初期化
		
		player.stop(); // 音声を止める。(2回目以降のため)
		
		stage.setTitle("PomodoroTimer");//タイトル設定
		
		stage.setWidth(700);
		stage.setHeight(850);//縦・横のサイズ設定
		
		tyuui.setFont(new Font(40));
		pomoboro.setFont(new Font(80));
		minutes.setFont(new Font(40));
		seconds.setFont(new Font(40)); 
		ato.setFont(new Font(40));  // ato,pomoboro,minutes,secondsのフォント設定
		
		//hintをインスタンス生成する。
		Tooltip hint = new Tooltip("ポモドーロタイマーは、集中して勉強や仕事をするために、"
				+ "時間を区切って効率をあげるタイマーです。タイマーが鳴ったら短い休憩を入れましょう。"
				+ "おすすめは、25分勉強して、5分休憩するサイクルです。");
		//hintの大きさを設定する
	    hint.setFont(new Font(20));
	    //pomoboroにhintを設定する
		pomoboro.setTooltip(hint);
	    
		AnchorPane.setTopAnchor(pomoboro, 0.0);
		AnchorPane.setLeftAnchor(pomoboro, 100.0);
		pomoborolabel.getChildren().addAll(pomoboro); // pomoboroの位置をpomoborolabelに設定。

		setminutes = new TextField("");
		setseconds = new TextField(""); // setminutes,setseconds（TextFild)の初期化

		setminutes.setMinSize(100,50);
		setseconds.setMinSize(100,50);
		start.setMinSize(200,80); 
		cheakalltime.setMinSize(200,40);//  各種ボタンの最小サイズを設定する。
		

		// toptomato,setminutes,minutes,setseconds,seconds,startの位置をsettimeに設定。
		AnchorPane.setTopAnchor(setminutes, 300.0);
		AnchorPane.setLeftAnchor(setminutes, 100.0);
		AnchorPane.setTopAnchor(setseconds, 300.0);
		AnchorPane.setLeftAnchor(setseconds, 350.0);
		AnchorPane.setTopAnchor(minutes, 300.0);
		AnchorPane.setLeftAnchor(minutes, 275.0);
		AnchorPane.setTopAnchor(seconds, 300.0);
		AnchorPane.setLeftAnchor(seconds, 525.0);
		AnchorPane.setTopAnchor(toptomato, 0.0);
		AnchorPane.setLeftAnchor(toptomato, 0.0);
		AnchorPane.setTopAnchor(start, 400.0);
		AnchorPane.setLeftAnchor(start, 230.0);
		AnchorPane.setTopAnchor(tyuui, 630.0);
		AnchorPane.setLeftAnchor(tyuui, 30.0);
		AnchorPane.setTopAnchor(cheakalltime,500.0);
		AnchorPane.setLeftAnchor(cheakalltime, 230.0);
		settime.getChildren().addAll(toptomato,setminutes,minutes,setseconds,seconds,start,tyuui,cheakalltime);
		
		start.setOnAction(event -> timerEvent(stage)); // startが押されたら、timerEventメソッドを実行
		
		cheakalltime.setOnAction(event -> alltimecheak(stage)); // cheakalltimeが押されたらallcheaktimeメソッドを実行
		
		// ウィンドウの×が押されたら、exitcheakメソッドに移動
		stage.setOnCloseRequest((WindowEvent event) -> {
			exitcheak(event);
		});
		
		root.getChildren().addAll(pomoborolabel,settime); // rootにpomoborolabel,settimeを設定
		
		stage.setScene(new Scene(root)); // rootをsceneにセットしそれをstageに設定
		stage.show(); // ウィンドウを表示する。
	
	}
	
	void alltimecheak(Stage stage){
		int fun=0,byou=0; //fun;分、byou：秒
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.OK);
		alert.setTitle("これまでの学習時間");
		fun = alltime/60; // これまでのタイマー稼働時間(分)を計算
		byou = alltime-(60*fun); //　これまでのタイマー稼働時間(秒)を計算
		// アラートにこれまでのタイマー稼働時間を表示
		alert.setHeaderText(String.valueOf(fun)+"分"+String.valueOf(byou)+"秒");
		//　30分未満のとき、「もっとがんばりましょう」と表示
		if(fun < 30){
		alert.setContentText("もっとがんばりましょう");
		}
		//　30分以上120分未満のとき、「そろそろ1休みしよう！」と表示
		else if(fun >= 30 && fun < 120){
			alert.setContentText("そろそろ一休みしよう！");
		}
		//　120分以上のとき、「よくがんばっていますね」と表示
		else if(fun >= 120){
			alert.setContentText("よくがんばっていますね！");
		}
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			start(stage);
		}
	}
	
	void timerEvent(Stage stage){   
		
		VBox timerV = new VBox(); //timerVのインスタンス生成（外に出せない)
		
		AnchorPane limit = new AnchorPane(); //　実行画面での時間表示 今使ってない (外に出せない)

		x=0; y=0; //タイマーの時間をリセット（2回目以降のため)

		try{

			x1 = Integer.parseInt(setminutes.getText()); // x1にsetminutesで設定した時間を代入
			x2 = Integer.parseInt(setseconds.getText()); // x2にsetsecondsで設定した時間を代入

			// x1とx2がどちらかでも0ならば、アラーとを出して警告、その後、finishメソッドに移動,flag=1にする
			if(x1<0 || x2<0){
				flag =1;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー1");
				alert.setHeaderText("タイマーがスタートできません");
				alert.setContentText("タイマーに正しい時間を設定してください。");
				alert.showAndWait();
				finish(stage);
			}

			// x1とx2がどちらも0ならば、アラーとを出して警告、その後、finishメソッドに移動,flag=1にする
			if(x1==0 && x2==0){
				flag = 1;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("エラー2");
				alert.setHeaderText("タイマーがスタートできません");
				alert.setContentText("タイマーに正しい時間を設定してください。");
				alert.showAndWait();
				finish(stage);
			}

			//　この先の処理は、正しい値が入力された場合のみ実行
			if(flag!=1){ 
				
				label1 = new Label("0"); // label1(表示する時間)に”0を設定(初期化) 
				label1.setFont(new Font(40)); // label1のフォント設定
			
				// ato,label1,secondsの位置を設定し、puttimeに設定
				AnchorPane.setTopAnchor(ato, 300.0);
				AnchorPane.setLeftAnchor(ato, 375.0);
				AnchorPane.setTopAnchor(label1, 300.0);
				AnchorPane.setLeftAnchor(label1, 475.0);
				//AnchorPane.setTopAnchor(seconds, 300.0);
				//AnchorPane.setLeftAnchor(seconds, 525.0);
				puttime.getChildren().addAll(ato,label1);
				
				timerV.getChildren().addAll(puttime); // timerVにputtimeを実行する。
				
				puttime.setVisible(false); //puttimeを非表示にする。
				
				timerV.getChildren().addAll(limit); // timerVにlimitを設定
				
				Scene vroot = new Scene(timerV); 
				stage.setScene(vroot); // vrootにtimerVにセットしそれをstageに設定
				
				vroot.setOnKeyPressed(event -> keyevent(stage,event,timerV)); // キーが押されたら、keyeventメソッドを実行
				vroot.setOnKeyReleased(event -> keyevent(stage,event,timerV)); // キーが離れたら、keyeventメソッドを実行
				
				timerV.setStyle("-fx-background-color: black;"); // timerV(背景)を黒色に設定する
				
				stage.setFullScreen(true); // stageをフルスクリーンにする
				
				
				x = x1 * 60 + x2; // 設定した時間の総数(秒)を計算し、xに代入
				
				nowtime = x;

				// timerのタイムライン
				Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						 //label1に現在の時間を設定
						nowtime = nowtime - 1;
						int fun1 = 0; // 現在時間(分)
						int byou1 = 0; // 現在時間（秒)
						fun1 = nowtime/60; // 分を計算
						byou1 = nowtime-(60*fun1); //　秒を計算
						label1.setText(String.valueOf(fun1)+"分"+String.valueOf(byou1)+"秒"); // label1に残り時間を設定
						
						y =  y+1; // 経過時間を計算

						// 経過時間　=　設定時間のとき
						if(x-y==0){
							finish(stage); //　finishメソッドを実行
						}
					}

				}));
				
				timer.setCycleCount(x); //　タイムラインを実行する回数
				timer.play(); // タイムラインを動かす。
				
			}
		}
		
		// 例外がおきたとき(主に入力の値が文字のときに実行される)
		catch(NumberFormatException ex){
			flag =1;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("エラー");
			alert.setHeaderText("タイマーがスタートできません");
			alert.setContentText("タイマーに正しい時間を設定してください。");
			alert.showAndWait();
			y=0;
			x=0;
			finish(stage);
		}
	}

	void finish(Stage stage){
		
		stage.setFullScreen(false); // フルスクリーンを解除する
		
		// flag=0(タイマーが実行された)なら音声を鳴らす。,alltimeに秒数を追加
		if(flag == 0){ 
			view.setMediaPlayer(player);    
			player.play();
			
			alltime = alltime + x;
		}
		
		exit(stage); // exitメソッドを実行
	}

	void exit(Stage stage){

		restart.setMinSize(200,80);
		exit.setMinSize(200,80); // restartとexitの大きさを設定

		VBox root2 = new VBox();  // root2のインスタンス生成
		
		AnchorPane pomoborolabel1 = new AnchorPane(); 
		pomoborolabel1.getChildren().addAll(pomoboro); //pomoborolabel1をインスタンス生成し、pomoboroを設定

		// settime1をインスタンス生成し、toptomato,restart,exitの位置を設定
		AnchorPane settime1 = new AnchorPane();
		AnchorPane.setTopAnchor(toptomato, 0.0);
		AnchorPane.setLeftAnchor(toptomato, 0.0);
		AnchorPane.setTopAnchor(restart, 350.0);
		AnchorPane.setLeftAnchor(restart, 90.0);
		AnchorPane.setTopAnchor(exit, 350.0);
		AnchorPane.setLeftAnchor(exit, 390.0);
		settime1.getChildren().addAll(toptomato,restart,exit);
		
		root2.getChildren().addAll(pomoborolabel1,settime1); //pomoborolabel1とsettime1をroot2に設定

		stage.setScene(new Scene(root2)); // root2をstageに設定

		restart.setOnAction(event -> start(stage)); // restartを押したらstartメソッドに戻る

		exit.setOnAction(event -> syuuryou(stage)); // exitを押したらsyuuryouメソッドを実行
		
	}
	
	// alertの「はい」ボタンが押されたら終了する(ウィンドウの×を押したとき)
	void exitcheak(WindowEvent event){
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.YES,ButtonType.NO);
		alert.setTitle("終了確認");
		alert.setHeaderText("タイマーを終了しますか");
		alert.setContentText("本当に終了しますか");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			System.exit(0);
		} else {
			event.consume();
		}
	}

	// alertの「はい」ボタンが押されたら終了する(終了ボタンを押したとき)
	void syuuryou(Stage stage){
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.YES,ButtonType.NO);
		alert.setTitle("終了確認");
		alert.setHeaderText("タイマーを終了しますか");
		alert.setContentText("本当に終了しますか");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			System.exit(0);
		} else {
			start(stage);
		}
	}

	// 実行中キーが押されたときに実行
	void keyevent(Stage stage,KeyEvent event,VBox timerV){
		
		// Shiftキーが押されたらputtimeを表示する。
		if(event.isShiftDown()==true){
			puttime.setVisible(true);
		}
		
		// Shiftキーが押されていないとき、puttimeを非表示にする。
		if(event.isShiftDown()==false){
			puttime.setVisible(false);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
