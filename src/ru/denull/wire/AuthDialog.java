package ru.denull.wire;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.denull.mtproto.DataService;
import ru.denull.mtproto.Server.RPCCallback;
import ru.denull.wire.model.Config;
import tl.auth.*;

public class AuthDialog extends JDialog {

  private JTextField phoneNumberField;
  private JTextField phonePrefixField;
  private CardLayout pages;
  private JButton loginBtn;
  private JTextField codeField;
  private JButton doneBtn;
  private JTextField firstNameField;
  private JTextField lastNameField;
  
  private DataService service = DataService.getInstance();
  
  private String phone;
  private SentCode code;
  private JButton backBtn;
  private JButton cancelBtn;
  private JCheckBox rememberCheck;

  /**
   * Create the dialog.
   */
  public AuthDialog(Window window, ModalityType modality) {
    super(window, modality);
    setBounds(window.getX() + (window.getWidth() - 500) / 2, window.getY() + (window.getHeight() - 240) / 2, 500, 240);
    getContentPane().setLayout(new BorderLayout());
    getRootPane().putClientProperty("apple.awt.documentModalSheet", "true");
    setResizable(false);
    
    JLabel iconLabel = new JLabel(new ImageIcon(Utils.getImage("icon72.png")));
    iconLabel.setVerticalAlignment(SwingConstants.TOP);
    iconLabel.setBorder(new EmptyBorder(20, 20, 20, 20));
    getContentPane().add(iconLabel, BorderLayout.WEST);
    
    pages = new CardLayout();
    final JPanel pagesPanel = new JPanel();
    pagesPanel.setLayout(pages);
    getContentPane().add(pagesPanel, BorderLayout.CENTER);
    
    final JPanel phoneContent = new JPanel();
    phoneContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    phoneContent.setBorder(new EmptyBorder(5, 0, 5, 5));
    pagesPanel.add(phoneContent, "phone");
    
    {     
      JLabel infoLabel = new JLabel("<html>Чтобы выполнить вход, укажите ваш номер мобильного телефона.<br/><br/>На него будет отправлен авторизационный код, который необходимо ввести на следующем шаге.</html>");
      infoLabel.setPreferredSize(new Dimension(370, 96));
      phoneContent.add(infoLabel);
      
      JLabel countryLabel = new JLabel("Страна:", SwingConstants.LEFT);
      countryLabel.setPreferredSize(new Dimension(70, countryLabel.getPreferredSize().height));
      phoneContent.add(countryLabel);
      
      JComboBox countryCombo = new JComboBox(Utils.getCountryNames());
      countryCombo.setPreferredSize(new Dimension(290, countryCombo.getPreferredSize().height));
      countryCombo.setSelectedIndex(172);
      phoneContent.add(countryCombo);
      
      JLabel phoneLabel = new JLabel("Телефон:", SwingConstants.LEFT);
      phoneLabel.setPreferredSize(new Dimension(71, phoneLabel.getPreferredSize().height));
      phoneContent.add(phoneLabel);
      
      phonePrefixField = new JTextField("+7");
      phonePrefixField.setHorizontalAlignment(JTextField.RIGHT);
      phonePrefixField.setPreferredSize(new Dimension(60, phonePrefixField.getPreferredSize().height));
      phoneContent.add(phonePrefixField);
      
      phoneNumberField = new JTextField();
      phoneNumberField.setPreferredSize(new Dimension(222, phoneNumberField.getPreferredSize().height));
      phoneNumberField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
          doneBtn.setEnabled(phoneNumberField.getText() != "");
        }
        public void insertUpdate(DocumentEvent e) {
          doneBtn.setEnabled(phoneNumberField.getText() != "");
        }
        public void changedUpdate(DocumentEvent e) {
          doneBtn.setEnabled(phoneNumberField.getText() != "");
        }
      });
      phoneContent.add(phoneNumberField);
      
      phoneNumberField.addAncestorListener( new RequestFocusListener() );
    }
    
    JPanel waitContent = new JPanel();
    waitContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    waitContent.setBorder(new EmptyBorder(5, 0, 5, 5));
    pagesPanel.add(waitContent, "wait");
    
    {
      JLabel infoLabel = new JLabel("<html>Подождите...</html>");
      infoLabel.setPreferredSize(new Dimension(370, 96));
      waitContent.add(infoLabel);
      
      JProgressBar progressBar = new JProgressBar();
      progressBar.setIndeterminate(true);
      progressBar.setPreferredSize(new Dimension(350, progressBar.getPreferredSize().height));
      waitContent.add(progressBar);
    }
    
    final JPanel codeContent = new JPanel();
    codeContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    codeContent.setBorder(new EmptyBorder(5, 0, 5, 5));
    pagesPanel.add(codeContent, "code");
    
    {
      JLabel infoLabel = new JLabel("<html>На указанный номер отправлен авторизационный код. Введите его, чтобы войти.</html>");
      infoLabel.setPreferredSize(new Dimension(370, 96));
      codeContent.add(infoLabel);
      
      JLabel codeLabel = new JLabel("Код:", SwingConstants.LEFT);
      codeLabel.setPreferredSize(new Dimension(70, codeLabel.getPreferredSize().height));
      codeContent.add(codeLabel);
      
      codeField = new JTextField();
      codeField.setPreferredSize(new Dimension(290, codeField.getPreferredSize().height));
      codeField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
          doneBtn.setEnabled(codeField.getText() != "");
        }
        public void insertUpdate(DocumentEvent e) {
          doneBtn.setEnabled(codeField.getText() != "");
        }
        public void changedUpdate(DocumentEvent e) {
          doneBtn.setEnabled(codeField.getText() != "");
        }
      });
      codeContent.add(codeField);
      
      rememberCheck = new JCheckBox("Запомнить меня");
      rememberCheck.setSelected(true);
      rememberCheck.setBorder(new EmptyBorder(0, 76, 0, 0));
      rememberCheck.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent e) {
          service.setStoragePolicy(rememberCheck.isSelected(), rememberCheck.isSelected(), rememberCheck.isSelected());
        }
      });
      codeContent.add(rememberCheck);
    }
    
    final JPanel registerContent = new JPanel();
    registerContent.setLayout(new FlowLayout(FlowLayout.LEFT));
    registerContent.setBorder(new EmptyBorder(5, 0, 5, 5));
    pagesPanel.add(registerContent, "register");
    
    {
      JLabel infoLabel = new JLabel("<html>Данный номер ещё не зарегистрирован.<br/><br/>Укажите свое имя, чтобы зарегистрироваться и начать использование Wire.</html>");
      infoLabel.setPreferredSize(new Dimension(370, 96));
      registerContent.add(infoLabel);
      
      JLabel firstNameLabel = new JLabel("Имя:", SwingConstants.LEFT);
      firstNameLabel.setPreferredSize(new Dimension(70, firstNameLabel.getPreferredSize().height));
      registerContent.add(firstNameLabel);
      
      firstNameField = new JTextField();
      firstNameField.setPreferredSize(new Dimension(290, firstNameField.getPreferredSize().height));
      firstNameField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
        public void insertUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
        public void changedUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
      });
      registerContent.add(firstNameField);
      
      JLabel lastNameLabel = new JLabel("Фамилия:", SwingConstants.LEFT);
      lastNameLabel.setPreferredSize(new Dimension(70, lastNameLabel.getPreferredSize().height));
      registerContent.add(lastNameLabel);
      
      lastNameField = new JTextField();
      lastNameField.setPreferredSize(new Dimension(290, lastNameField.getPreferredSize().height));
      lastNameField.getDocument().addDocumentListener(new DocumentListener() {
        public void removeUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
        public void insertUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
        public void changedUpdate(DocumentEvent e) {
          doneBtn.setEnabled(!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty());
        }
      });
      registerContent.add(lastNameField);
    }
    
    {
      JPanel buttonPane = new JPanel();
      buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
      getContentPane().add(buttonPane, BorderLayout.SOUTH);
      
      {
        backBtn = new JButton("« Назад");
        backBtn.setActionCommand("Back");
        backBtn.setVisible(false);
        backBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            backBtn.setVisible(false);
            doneBtn.setVisible(true);
            doneBtn.setEnabled(true);
            doneBtn.setText("Отправить код");
            pages.show(pagesPanel, "phone");
            phoneNumberField.requestFocusInWindow();
          }
        });
        buttonPane.add(backBtn);
      }
      {
        cancelBtn = new JButton("Отмена");
        cancelBtn.setActionCommand("Cancel");
        cancelBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            setVisible(false);
            System.exit(0);
          }
        });
        buttonPane.add(cancelBtn);
      }
      {
        doneBtn = new JButton("Отправить код");
        doneBtn.setActionCommand("OK");
        doneBtn.setEnabled(false);
        doneBtn.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent event) {
            if (phoneContent.isVisible()) { // SEND CODE 
              phone = phonePrefixField.getText() + phoneNumberField.getText();
              
              if (service.mainServer == null) {
                //alert("Unable to connect to server");
                return;
              }
              //progress = ProgressDialog.show(this, "Please wait...", "Connecting...", true);
              
              service.mainServer.call(new SendCode(phone, 0, Config.api_id, Config.api_hash, "ru"), new RPCCallback<SentCode>() {
                public void done(SentCode result) {
                  System.out.println("Code sent!");
                  service.setPhone(phone);
                  code = result;
                  
                  backBtn.setVisible(true);
                  doneBtn.setVisible(true);
                  doneBtn.setText(code.phone_registered ? "Войти" : "Продолжить");
                  doneBtn.setEnabled(false);
                  codeField.setText("");
                  pages.show(pagesPanel, "code");
                  codeField.requestFocusInWindow();
                }
  
                public void error(int code, final String message) {
                  System.out.println("Error while trying to send code: " + message);
                  
                  backBtn.setVisible(false);
                  doneBtn.setVisible(true);
                  doneBtn.setEnabled(true);
                  doneBtn.setText("Отправить код");
                  pages.show(pagesPanel, "phone");
                  phoneNumberField.requestFocusInWindow();
                }
              });
              
              backBtn.setVisible(false);
              doneBtn.setVisible(false);
              pages.show(pagesPanel, "wait");
            } else
            if (codeContent.isVisible()) { // LOGIN / REGISTER
              if (code.phone_registered) {
                service.mainServer.call(new SignIn(phone, code.phone_code_hash, codeField.getText()), new RPCCallback<Authorization>() {
                  public void done(Authorization result) {
                    setVisible(false);
                    Main.window.authorized(result.user);
                  }
    
                  public void error(int errorCode, final String message) {
                    backBtn.setVisible(true);
                    doneBtn.setVisible(true);
                    doneBtn.setText("Войти");
                    doneBtn.setEnabled(false);
                    codeField.setText("");
                    pages.show(pagesPanel, "code");
                    codeField.requestFocusInWindow();
                  }
                });

                backBtn.setVisible(false);
                doneBtn.setVisible(false);
                pages.show(pagesPanel, "wait");
              } else {
                backBtn.setVisible(true);
                doneBtn.setVisible(true);
                doneBtn.setText("Войти");
                doneBtn.setEnabled(false);
                pages.show(pagesPanel, "register");
                firstNameField.requestFocusInWindow();
              }
            } else
            if (registerContent.isVisible()) { // REGISTER AND LOGIN
              service.mainServer.call(new SignUp(phone, code.phone_code_hash, codeField.getText(), firstNameField.getText(), lastNameField.getText()), new RPCCallback<Authorization>() {
                public void done(Authorization result) {
                  setVisible(false);
                  Main.window.authorized(result.user);
                }
  
                public void error(int errorCode, final String message) {
                  firstNameField.requestFocusInWindow();
                }
              });
              
              backBtn.setVisible(false);
              doneBtn.setVisible(false);
              pages.show(pagesPanel, "wait");
            }
          }
        });
        buttonPane.add(doneBtn);
        getRootPane().setDefaultButton(doneBtn);
      }
    }
  } 

}
