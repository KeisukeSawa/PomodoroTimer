
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
	TextField setseconds;  // ���A�b��ݒ肷��TextField�̃C���X�^���X����
	
	Label minutes = new Label("��");
	Label seconds = new Label("�b");
	Label pomoboro = new Label("�|���h�[���^�C�}�[");
	Label label1 = new Label("0");
	Label ato = new Label("����"); 
	Label tyuui = new Label("Shift�L�[�������Ǝc�莞�Ԃ�������܂�");//�e���Label�̃C���X�^���X����
	
	
	Button start = new Button("�X�^�[�g");
	Button exit = new Button("�I��");
	Button restart = new Button("������"); 
	Button cheakalltime = new Button("����܂ł̊w�K����");//�e��̃{�^���̃C���X�^���X����

	ImageView toptomato = new ImageView("toptomato.png"); // �g�}�g�̉摜��ݒ肵�AImageView���C���X�^���X����
		
	VBox root = new VBox(); //�e��VBox�̃C���X�^���X����
	
	int x1=0,x2=0,x=0,y=0,flag=0; // x1:�ݒ肵�����Ax2:�ݒ肵���b�Ax�F�ݒ肵�����Ԃ̍��v(�b)�Ay�F�o�ߎ��ԁAflag�F�������l�����͂��ꂽ��

	Path path = Paths.get("hatodokei.mp3");
	String onsei = path.toUri().toString();
	Media media = new Media(onsei);
	MediaPlayer player = new MediaPlayer(media); 
	MediaView view = new MediaView(); // �����̐ݒ�

	AnchorPane puttime = new AnchorPane(); // �e��AnchorPane�̃C���X�^���X����
	
	int alltime=0; // ���܂ł̃^�C�}�[�ғ����Ԃ̍��v
	
	int nowtime = 0; //���݂̌o�ߎ���
	
	@Override
	public void start(Stage stage) {
		
		//�@�e��A���J�[�y�C���̃C���X�^���X����(start���\�b�h�̒��łȂ���΂����Ȃ�)
		AnchorPane pomoborolabel = new AnchorPane(); // ��ʏ㕔�̃^�C�g��
	    AnchorPane settime = new AnchorPane();  // �X�^�[�g���(�g�}�g�ƃg�}�g�̒�)
	    puttime = new AnchorPane();
		
		VBox root = new VBox(); //�e��VBox�̃C���X�^���X����
		
		flag = 0; // flag�̏�����
		
		player.stop(); // �������~�߂�B(2��ڈȍ~�̂���)
		
		stage.setTitle("PomodoroTimer");//�^�C�g���ݒ�
		
		stage.setWidth(700);
		stage.setHeight(850);//�c�E���̃T�C�Y�ݒ�
		
		tyuui.setFont(new Font(40));
		pomoboro.setFont(new Font(80));
		minutes.setFont(new Font(40));
		seconds.setFont(new Font(40)); 
		ato.setFont(new Font(40));  // ato,pomoboro,minutes,seconds�̃t�H���g�ݒ�
		
		//hint���C���X�^���X��������B
		Tooltip hint = new Tooltip("�|���h�[���^�C�}�[�́A�W�����ĕ׋���d�������邽�߂ɁA"
				+ "���Ԃ���؂��Č�����������^�C�}�[�ł��B�^�C�}�[��������Z���x�e�����܂��傤�B"
				+ "�������߂́A25���׋����āA5���x�e����T�C�N���ł��B");
		//hint�̑傫����ݒ肷��
	    hint.setFont(new Font(20));
	    //pomoboro��hint��ݒ肷��
		pomoboro.setTooltip(hint);
	    
		AnchorPane.setTopAnchor(pomoboro, 0.0);
		AnchorPane.setLeftAnchor(pomoboro, 100.0);
		pomoborolabel.getChildren().addAll(pomoboro); // pomoboro�̈ʒu��pomoborolabel�ɐݒ�B

		setminutes = new TextField("");
		setseconds = new TextField(""); // setminutes,setseconds�iTextFild)�̏�����

		setminutes.setMinSize(100,50);
		setseconds.setMinSize(100,50);
		start.setMinSize(200,80); 
		cheakalltime.setMinSize(200,40);//  �e��{�^���̍ŏ��T�C�Y��ݒ肷��B
		

		// toptomato,setminutes,minutes,setseconds,seconds,start�̈ʒu��settime�ɐݒ�B
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
		
		start.setOnAction(event -> timerEvent(stage)); // start�������ꂽ��AtimerEvent���\�b�h�����s
		
		cheakalltime.setOnAction(event -> alltimecheak(stage)); // cheakalltime�������ꂽ��allcheaktime���\�b�h�����s
		
		// �E�B���h�E�́~�������ꂽ��Aexitcheak���\�b�h�Ɉړ�
		stage.setOnCloseRequest((WindowEvent event) -> {
			exitcheak(event);
		});
		
		root.getChildren().addAll(pomoborolabel,settime); // root��pomoborolabel,settime��ݒ�
		
		stage.setScene(new Scene(root)); // root��scene�ɃZ�b�g�������stage�ɐݒ�
		stage.show(); // �E�B���h�E��\������B
	
	}
	
	void alltimecheak(Stage stage){
		int fun=0,byou=0; //fun;���Abyou�F�b
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.OK);
		alert.setTitle("����܂ł̊w�K����");
		fun = alltime/60; // ����܂ł̃^�C�}�[�ғ�����(��)���v�Z
		byou = alltime-(60*fun); //�@����܂ł̃^�C�}�[�ғ�����(�b)���v�Z
		// �A���[�g�ɂ���܂ł̃^�C�}�[�ғ����Ԃ�\��
		alert.setHeaderText(String.valueOf(fun)+"��"+String.valueOf(byou)+"�b");
		//�@30�������̂Ƃ��A�u�����Ƃ���΂�܂��傤�v�ƕ\��
		if(fun < 30){
		alert.setContentText("�����Ƃ���΂�܂��傤");
		}
		//�@30���ȏ�120�������̂Ƃ��A�u���낻��1�x�݂��悤�I�v�ƕ\��
		else if(fun >= 30 && fun < 120){
			alert.setContentText("���낻���x�݂��悤�I");
		}
		//�@120���ȏ�̂Ƃ��A�u�悭����΂��Ă��܂��ˁv�ƕ\��
		else if(fun >= 120){
			alert.setContentText("�悭����΂��Ă��܂��ˁI");
		}
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			start(stage);
		}
	}
	
	void timerEvent(Stage stage){   
		
		VBox timerV = new VBox(); //timerV�̃C���X�^���X�����i�O�ɏo���Ȃ�)
		
		AnchorPane limit = new AnchorPane(); //�@���s��ʂł̎��ԕ\�� ���g���ĂȂ� (�O�ɏo���Ȃ�)

		x=0; y=0; //�^�C�}�[�̎��Ԃ����Z�b�g�i2��ڈȍ~�̂���)

		try{

			x1 = Integer.parseInt(setminutes.getText()); // x1��setminutes�Őݒ肵�����Ԃ���
			x2 = Integer.parseInt(setseconds.getText()); // x2��setseconds�Őݒ肵�����Ԃ���

			// x1��x2���ǂ��炩�ł�0�Ȃ�΁A�A���[�Ƃ��o���Čx���A���̌�Afinish���\�b�h�Ɉړ�,flag=1�ɂ���
			if(x1<0 || x2<0){
				flag =1;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�G���[1");
				alert.setHeaderText("�^�C�}�[���X�^�[�g�ł��܂���");
				alert.setContentText("�^�C�}�[�ɐ��������Ԃ�ݒ肵�Ă��������B");
				alert.showAndWait();
				finish(stage);
			}

			// x1��x2���ǂ����0�Ȃ�΁A�A���[�Ƃ��o���Čx���A���̌�Afinish���\�b�h�Ɉړ�,flag=1�ɂ���
			if(x1==0 && x2==0){
				flag = 1;
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("�G���[2");
				alert.setHeaderText("�^�C�}�[���X�^�[�g�ł��܂���");
				alert.setContentText("�^�C�}�[�ɐ��������Ԃ�ݒ肵�Ă��������B");
				alert.showAndWait();
				finish(stage);
			}

			//�@���̐�̏����́A�������l�����͂��ꂽ�ꍇ�̂ݎ��s
			if(flag!=1){ 
				
				label1 = new Label("0"); // label1(�\�����鎞��)�Ɂh0��ݒ�(������) 
				label1.setFont(new Font(40)); // label1�̃t�H���g�ݒ�
			
				// ato,label1,seconds�̈ʒu��ݒ肵�Aputtime�ɐݒ�
				AnchorPane.setTopAnchor(ato, 300.0);
				AnchorPane.setLeftAnchor(ato, 375.0);
				AnchorPane.setTopAnchor(label1, 300.0);
				AnchorPane.setLeftAnchor(label1, 475.0);
				//AnchorPane.setTopAnchor(seconds, 300.0);
				//AnchorPane.setLeftAnchor(seconds, 525.0);
				puttime.getChildren().addAll(ato,label1);
				
				timerV.getChildren().addAll(puttime); // timerV��puttime�����s����B
				
				puttime.setVisible(false); //puttime���\���ɂ���B
				
				timerV.getChildren().addAll(limit); // timerV��limit��ݒ�
				
				Scene vroot = new Scene(timerV); 
				stage.setScene(vroot); // vroot��timerV�ɃZ�b�g�������stage�ɐݒ�
				
				vroot.setOnKeyPressed(event -> keyevent(stage,event,timerV)); // �L�[�������ꂽ��Akeyevent���\�b�h�����s
				vroot.setOnKeyReleased(event -> keyevent(stage,event,timerV)); // �L�[�����ꂽ��Akeyevent���\�b�h�����s
				
				timerV.setStyle("-fx-background-color: black;"); // timerV(�w�i)�����F�ɐݒ肷��
				
				stage.setFullScreen(true); // stage���t���X�N���[���ɂ���
				
				
				x = x1 * 60 + x2; // �ݒ肵�����Ԃ̑���(�b)���v�Z���Ax�ɑ��
				
				nowtime = x;

				// timer�̃^�C�����C��
				Timeline timer = new Timeline(new KeyFrame(Duration.millis(1000), new EventHandler<ActionEvent>(){
					@Override
					public void handle(ActionEvent event) {
						 //label1�Ɍ��݂̎��Ԃ�ݒ�
						nowtime = nowtime - 1;
						int fun1 = 0; // ���ݎ���(��)
						int byou1 = 0; // ���ݎ��ԁi�b)
						fun1 = nowtime/60; // �����v�Z
						byou1 = nowtime-(60*fun1); //�@�b���v�Z
						label1.setText(String.valueOf(fun1)+"��"+String.valueOf(byou1)+"�b"); // label1�Ɏc�莞�Ԃ�ݒ�
						
						y =  y+1; // �o�ߎ��Ԃ��v�Z

						// �o�ߎ��ԁ@=�@�ݒ莞�Ԃ̂Ƃ�
						if(x-y==0){
							finish(stage); //�@finish���\�b�h�����s
						}
					}

				}));
				
				timer.setCycleCount(x); //�@�^�C�����C�������s�����
				timer.play(); // �^�C�����C���𓮂����B
				
			}
		}
		
		// ��O���������Ƃ�(��ɓ��͂̒l�������̂Ƃ��Ɏ��s�����)
		catch(NumberFormatException ex){
			flag =1;
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("�G���[");
			alert.setHeaderText("�^�C�}�[���X�^�[�g�ł��܂���");
			alert.setContentText("�^�C�}�[�ɐ��������Ԃ�ݒ肵�Ă��������B");
			alert.showAndWait();
			y=0;
			x=0;
			finish(stage);
		}
	}

	void finish(Stage stage){
		
		stage.setFullScreen(false); // �t���X�N���[������������
		
		// flag=0(�^�C�}�[�����s���ꂽ)�Ȃ特����炷�B,alltime�ɕb����ǉ�
		if(flag == 0){ 
			view.setMediaPlayer(player);    
			player.play();
			
			alltime = alltime + x;
		}
		
		exit(stage); // exit���\�b�h�����s
	}

	void exit(Stage stage){

		restart.setMinSize(200,80);
		exit.setMinSize(200,80); // restart��exit�̑傫����ݒ�

		VBox root2 = new VBox();  // root2�̃C���X�^���X����
		
		AnchorPane pomoborolabel1 = new AnchorPane(); 
		pomoborolabel1.getChildren().addAll(pomoboro); //pomoborolabel1���C���X�^���X�������Apomoboro��ݒ�

		// settime1���C���X�^���X�������Atoptomato,restart,exit�̈ʒu��ݒ�
		AnchorPane settime1 = new AnchorPane();
		AnchorPane.setTopAnchor(toptomato, 0.0);
		AnchorPane.setLeftAnchor(toptomato, 0.0);
		AnchorPane.setTopAnchor(restart, 350.0);
		AnchorPane.setLeftAnchor(restart, 90.0);
		AnchorPane.setTopAnchor(exit, 350.0);
		AnchorPane.setLeftAnchor(exit, 390.0);
		settime1.getChildren().addAll(toptomato,restart,exit);
		
		root2.getChildren().addAll(pomoborolabel1,settime1); //pomoborolabel1��settime1��root2�ɐݒ�

		stage.setScene(new Scene(root2)); // root2��stage�ɐݒ�

		restart.setOnAction(event -> start(stage)); // restart����������start���\�b�h�ɖ߂�

		exit.setOnAction(event -> syuuryou(stage)); // exit����������syuuryou���\�b�h�����s
		
	}
	
	// alert�́u�͂��v�{�^���������ꂽ��I������(�E�B���h�E�́~���������Ƃ�)
	void exitcheak(WindowEvent event){
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.YES,ButtonType.NO);
		alert.setTitle("�I���m�F");
		alert.setHeaderText("�^�C�}�[���I�����܂���");
		alert.setContentText("�{���ɏI�����܂���");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			System.exit(0);
		} else {
			event.consume();
		}
	}

	// alert�́u�͂��v�{�^���������ꂽ��I������(�I���{�^�����������Ƃ�)
	void syuuryou(Stage stage){
		Alert alert = new Alert(AlertType.INFORMATION,"",ButtonType.YES,ButtonType.NO);
		alert.setTitle("�I���m�F");
		alert.setHeaderText("�^�C�}�[���I�����܂���");
		alert.setContentText("�{���ɏI�����܂���");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.YES) {
			System.exit(0);
		} else {
			start(stage);
		}
	}

	// ���s���L�[�������ꂽ�Ƃ��Ɏ��s
	void keyevent(Stage stage,KeyEvent event,VBox timerV){
		
		// Shift�L�[�������ꂽ��puttime��\������B
		if(event.isShiftDown()==true){
			puttime.setVisible(true);
		}
		
		// Shift�L�[��������Ă��Ȃ��Ƃ��Aputtime���\���ɂ���B
		if(event.isShiftDown()==false){
			puttime.setVisible(false);
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
