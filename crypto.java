
package harry;

import java.awt.Choice;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.EmptyStackException;
import java.util.Iterator;
import javafx.event.ActionEvent;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
*
* @author HARRY FERNANDEZ
*/
@SuppressWarnings("serial")
public class Crypto extends JFrame{

// Image Steganography

private StegoImage stegoImg;
private String imgFormat;
private File embedFile;
private JLabel ifilesize;
private JPanel contentPane;
public String passwd = null;
public String user_data = null;
public String encrypted_data = null;
public String tmp_buff = null;
byte[] encrypted = null;
byte[] decrypted = null;
private JTextField target_image_textField;
private JTextField itarget_file_textField;
private JTextField itarget_key_textField;

    public static void main(String[] harry) {
        
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    Crypto frame = new Crypto();
                    frame.setVisible(true);
                } catch (Exception e) {
                }
            }
        });
    }

    public Crypto() {
        
        setIconImage(Toolkit.getDefaultToolkit().getImage(Crypto.class.getResource("/Images/safe-backup-icon.png")));
        setResizable(false);
        setTitle("Crypto Encryption and Decryption");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 538, 566);
        
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane_1.setBounds(10, 11, 512, 515);
        tabbedPane_1.setBackground(Color.WHITE);
        contentPane.add(tabbedPane_1);

        // Starting Pane for Text Ecryption

        JPanel text_encryption_panel = new JPanel();
        tabbedPane_1.addTab("Text Encryption", null, text_encryption_panel, null);
        text_encryption_panel.setLayout(null);

        // Create the debugging area

        TextArea debug = new TextArea();
        debug.setBounds(10, 378, 477, 99);
        debug.setFont(new Font("Monospaced", Font.PLAIN, 9));
        debug.setBackground(Color.BLACK);
        debug.setForeground(Color.WHITE);
        debug.setEditable(false);
        text_encryption_panel.add(debug);

        // Create the choice options
        
        Choice choice = new Choice();
        choice.setBounds(10, 313, 193, 20);
        text_encryption_panel.add(choice);

        // Create and a key for the user on startup

        TextField passwordField = new TextField();
        passwordField.setBounds(227, 311, 166, 22);
        passwordField.setEditable(true);
        text_encryption_panel.add(passwordField);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 11, 477, 266);
        text_encryption_panel.add(scrollPane);

        // Area for the user to add text to be encrypted
        
        JTextArea user_input = new JTextArea();
        user_input.setFont(new Font("Hack", Font.PLAIN, 14));
        user_input.setText("Welcome To Crypto, Update the Text Here!");
        user_input.setToolTipText("Text to Encrypt...");
        scrollPane.setViewportView(user_input);

        JLabel lblNewLabel = new JLabel("Enter Authentication Key");
        lblNewLabel.setBounds(227, 291, 150, 14);
        text_encryption_panel.add(lblNewLabel);

        JLabel lblDebugoutput = new JLabel("Crypto Process");
        lblDebugoutput.setBounds(10, 358, 88, 14);
        lblDebugoutput.setFont(new Font("Tahoma", Font.PLAIN, 9));
        text_encryption_panel.add(lblDebugoutput);
        
        JLabel lblMode = new JLabel("Mode");
        lblMode.setBounds(10, 293, 150, 14);
        text_encryption_panel.add(lblMode);

        JButton GO_button = new JButton("Crypto");
        GO_button.setBounds(399, 339, 88, 22);
        text_encryption_panel.add(GO_button);

        JButton Save_button = new JButton("Save Key");
        Save_button.setBounds(212, 339, 88, 22);
        text_encryption_panel.add(Save_button);
        Save_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String save_pass = passwordField.getText();
                try {
                    DateFormat df = new SimpleDateFormat("dd-MM-yy_HH-mm-ss");
                    Calendar calobj = Calendar.getInstance();
                    String file_name = df.format(calobj.getTime()) + "_harry.txt";
                    PrintWriter writer = new PrintWriter(file_name, "UTF-8");
                    writer.println(save_pass);
                    debug.append("Key Saved to File: " + file_name + "\n");
                    writer.close(); 
                }   
                catch (FileNotFoundException e1) {
                    debug.append("### Saving Error: FileNotFoundException\n");
                }
                catch (UnsupportedEncodingException e1) {
                    debug.append("### Saving Error: UnsupportedEncodingException\n");
                }
            }
        });
        Save_button.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JButton Gen_button = new JButton("Keygen");
        Gen_button.setBounds(399, 311, 88, 22);
        text_encryption_panel.add(Gen_button);
        Gen_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                debug.append("New Key was Created...\n");
                KeyGenerator gen = null;
                try {
                    gen = KeyGenerator.getInstance("AES");
                } 
                catch (NoSuchAlgorithmException e1) {
                }
                gen.init(128); /* 128-bit AES */
                SecretKey secret = gen.generateKey();
                byte[] binary = secret.getEncoded();
                String key = String.format("%032X", new BigInteger(+1, binary));
                key = key.substring(0, Math.min(key.length(), 16));
                passwordField.setText(key);
            }   
        });
        Gen_button.setFont(new Font("Tahoma", Font.BOLD, 12));

        JButton Load_button = new JButton("Load Key");
        Load_button.setBounds(305, 339, 88, 22);
        text_encryption_panel.add(Load_button);
        Load_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                File workingDirectory = new File(System.getProperty("user.dir"));
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(workingDirectory);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try (BufferedReader buffer = new BufferedReader(new FileReader(selectedFile))) {
                        String firstLine = buffer.readLine();
                        passwordField.setText(firstLine);
                    } 
                    catch (IOException ex) {
                        debug.append("### Loading Error: IOException\n");
                    }
                }
            }
        });
        Load_button.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JButton Clear_button = new JButton("Clear");
        Clear_button.setForeground(Color.BLACK);
        Clear_button.setBounds(399, 283, 88, 22);
        text_encryption_panel.add(Clear_button);
        Clear_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                user_input.setText("");
                debug.append("Text Box Cleared...\n");
            }
        });

        GO_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String current_mode = choice.getSelectedItem();		// picking the current mode
                String user_data = user_input.getText();
                String passwd = passwordField.getText();
                if(user_data==null || user_data.isEmpty()){
                    user_data = null;
                }
                if(passwd==null || passwd.isEmpty()){
                    passwd = null;
                }

                // Debugging data out to the user
                debug.append("Running Mode: " + current_mode + "\n");	// debug out to the user..
                if(user_data == null || passwd == null){
                    // Check that the user has given a password and Text data!
                    debug.append("### ERROR, Please make sure you have entered a Private Key and Encryptable Text ### \n");
                    JOptionPane.showMessageDialog(null,"Please make sure you have entered a Private Key and Encryptable Text");
                    throw new EmptyStackException();
                }
                debug.append("Ingesting your Key...\n");
                Key aesKey = new SecretKeySpec(passwd.getBytes(), "AES");
                try {
                    Cipher cipher = Cipher.getInstance("AES");
                    if (current_mode == "Encrypt"){
                        debug.append("Working on Encrypting data...\n");
                        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
                        byte[] encrypted = cipher.doFinal(user_data.getBytes());
                        tmp_buff = new String(Base64.getEncoder().encodeToString(encrypted));
                        user_input.setText(tmp_buff);
                    }
                    if(current_mode == "Decrypt"){
                        debug.append("Working on Decrypting data...\n");
                        byte[] encrypted = Base64.getDecoder().decode(user_data);
                        cipher.init(Cipher.DECRYPT_MODE, aesKey);
                        String decrypted = new String(cipher.doFinal(encrypted));
                        user_input.setText(decrypted);
                    }
                    debug.append("##################### END OF PROCESS #####################\n");
                } 
                catch (Exception e) {
                    debug.append(e.toString() + "\n");
                } 
            }
        });
        choice.add("Encrypt");
        choice.add("Decrypt");
        KeyGenerator gen = null;
        try {
            gen = KeyGenerator.getInstance("AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        gen.init(128); /* 128-bit AES */
        SecretKey secret = gen.generateKey();
        byte[] binary = secret.getEncoded();
        String key = String.format("%032X", new BigInteger(+1, binary));
        key = key.substring(0, Math.min(key.length(), 16));

        // Starting Pane for Image Steganography

        JPanel image_steganography = new JPanel();
        tabbedPane_1.addTab("Image Steganography", null, image_steganography, null);
        image_steganography.setLayout(null);

        Choice istg_set_mode = new Choice();
        istg_set_mode.setBounds(10, 342, 182, 36);
        istg_set_mode.add("Embed");
        istg_set_mode.add("Extract");

        TextArea istg_debug_area = new TextArea();
        istg_debug_area.setFont(new Font("Monospaced", Font.PLAIN, 9));
        istg_debug_area.setBounds(10, 381, 487, 96);
        istg_debug_area.setBackground(Color.BLACK);
        istg_debug_area.setForeground(Color.WHITE);
        image_steganography.add(istg_debug_area);
        image_steganography.add(istg_set_mode);

        JLabel iLabel1=new JLabel("Cover Image");
        JLabel iLabel2=new JLabel("Hide Capacity");
        JLabel iLabel3=new JLabel("File");
        JLabel iLabel4=new JLabel("File Size");
        JLabel iLabel5=new JLabel("Key");
        iLabel1.setBounds(10, 12, 150, 16);
        image_steganography.add(iLabel1);
        iLabel2.setBounds(10, 42, 150, 16);
        image_steganography.add(iLabel2);
        iLabel3.setBounds(10, 72, 150, 16);
        image_steganography.add(iLabel3);
        iLabel4.setBounds(10, 102, 150, 16);
        image_steganography.add(iLabel4);
        iLabel5.setBounds(10, 132, 150, 16);
        image_steganography.add(iLabel5);


        target_image_textField = new JTextField();
        target_image_textField.setToolTipText("Path to Image");
        target_image_textField.setEditable(false);
        target_image_textField.setBackground(Color.WHITE);
        target_image_textField.setBounds(120, 12, 200, 20);
        target_image_textField.setColumns(10);
        image_steganography.add(target_image_textField);

        JLabel imgsize=new JLabel("");
        imgsize.setBounds(120,42,200,20);
        image_steganography.add(imgsize);

        itarget_file_textField = new JTextField();
        itarget_file_textField.setToolTipText("Path to File");
        itarget_file_textField.setEditable(false);
        itarget_file_textField.setBounds(120, 72, 200, 30);
        itarget_file_textField.setBackground(Color.WHITE);
        itarget_file_textField.setColumns(10);
        image_steganography.add(itarget_file_textField);
        
        ifilesize=new JLabel("");
        ifilesize.setBounds(120,102,200,20);
        image_steganography.add(ifilesize);

        itarget_key_textField = new JTextField();
        itarget_key_textField.setBounds(120, 132, 200, 30);
        itarget_key_textField.setColumns(10);
        image_steganography.add(itarget_key_textField);

        JButton audbrowse = new JButton("Browse");
        audbrowse.setBounds(380, 12, 100, 30);
        audbrowse.setFocusable(false);
        image_steganography.add(audbrowse);
        audbrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                File image = selectImage();
                if (image == null)
                    return;
                try {
                    if(istg_set_mode.getSelectedItem() == "Embed")
                        stegoImg = new StegoImage(ImageIO.read(image), StegoImage.HIDE_MODE);
                        if(istg_set_mode.getSelectedItem() == "Extract")
                        stegoImg = new StegoImage(ImageIO.read(image), StegoImage.EXTRACT_MODE);
                        target_image_textField.setText(image.getName());
                        istg_debug_area.append("Target Image Selected...\n");
                        imgsize.setText(stegoImg.getMaxHideCapacity()/1024 + " Kilo Bytes");
                        imgFormat = getImageFormat(image);
                } catch (Exception e) {
                }
            }		
        });

        JButton ifilebrowse = new JButton("Browse");
        ifilebrowse.setBounds(380, 72, 100, 30);
        image_steganography.add(ifilebrowse);
        ifilebrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                selectFileForImage();
            }
        });

        JButton istg_go_button = new JButton("Stegno");
        istg_go_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String image_path = target_image_textField.getText();
                if(!image_path.contains(".png")){

                    // Check that the user has given an image path
                    istg_debug_area.append("Please make sure you have given the path to an Image...");
                    JOptionPane.showMessageDialog(null,"Please make sure you have given the path to an Image");
                    target_image_textField.setText("");
                    throw new EmptyStackException();
                }
                Steganography steg = new Steganography();
                String current_mode = istg_set_mode.getSelectedItem();

                // picking the current mode
                if (current_mode == "Embed"){
                    hideFileIntoImage();
                    istg_debug_area.append("Done Inserting the File, into your Image...\n");
                }
                if(current_mode == "Extract"){
                    extractFileFromImage();
                    istg_debug_area.append("Done Extracting the File, from your Image...\n");
                } 
            }
        });
        istg_go_button.setBounds(298, 326, 85, 36);
        image_steganography.add(istg_go_button);

        JButton ihelp_button = new JButton("Help");
        ihelp_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                istg_debug_area.append("Getting help Information...\n");
                String help_text = "                                           Need Some Help?\n"
                    + "1. Type out the Text you want to hide in the Image\n"
                    + "2. Select a Target image using the 'Path' Button, The Image has to be a (.jpg or .png)\n"
                    + "3. Pick your mode (Embed / Extract)\n"
                    + "4. Then simply Hit 'Stegno' and your text will be hidden or shown\n";
                    JOptionPane.showMessageDialog(null, help_text);
            }
        });
        ihelp_button.setBounds(429, 326, 70, 36);
        image_steganography.add(ihelp_button);

        JLabel mode_lab = new JLabel("Mode");
        mode_lab.setBounds(10, 322, 150, 14);
        image_steganography.add(mode_lab);

        // Starting Pane for Audio Steganography

        JPanel audio_steganography = new JPanel();
        tabbedPane_1.addTab("Audio Steganography", null, audio_steganography, null);
        audio_steganography.setLayout(null);

        Choice astg_set_mode = new Choice();
        astg_set_mode.setBounds(10, 342, 182, 36);
        astg_set_mode.add("Embed");
        astg_set_mode.add("Extract");

        TextArea astg_debug_area = new TextArea();
        astg_debug_area.setFont(new Font("Monospaced", Font.PLAIN, 9));
        astg_debug_area.setBounds(10, 381, 487, 96);
        astg_debug_area.setBackground(Color.BLACK);
        astg_debug_area.setForeground(Color.WHITE);
        audio_steganography.add(astg_debug_area);
        audio_steganography.add(astg_set_mode);

        JLabel aLabel1=new JLabel("Cover Image");
        JLabel aLabel2=new JLabel("Hide Capacity");
        JLabel aLabel3=new JLabel("File");
        JLabel aLabel4=new JLabel("File Size");
        JLabel aLabel5=new JLabel("Key");
        aLabel1.setBounds(10, 12, 150, 16);
        audio_steganography.add(aLabel1);
        aLabel2.setBounds(10, 42, 150, 16);
        audio_steganography.add(aLabel2);
        aLabel3.setBounds(10, 72, 150, 16);
        audio_steganography.add(aLabel3);
        aLabel4.setBounds(10, 102, 150, 16);
        audio_steganography.add(aLabel4);
        aLabel5.setBounds(10, 132, 150, 16);
        audio_steganography.add(aLabel5);


        target_audio_textField = new JTextField();
        target_audio_textField.setToolTipText("Path to Image");
        target_audio_textField.setEditable(false);
        target_audio_textField.setBackground(Color.WHITE);
        target_audio_textField.setBounds(120, 12, 200, 20);
        target_audio_textField.setColumns(10);
        audio_steganography.add(target_audio_textField);

        JLabel audsize=new JLabel("");
        audsize.setBounds(120,42,200,20);
        audio_steganography.add(audsize);

        atarget_file_textField = new JTextField();
        atarget_file_textField.setToolTipText("Path to File");
        atarget_file_textField.setEditable(false);
        atarget_file_textField.setBounds(120, 72, 200, 30);
        atarget_file_textField.setBackground(Color.WHITE);
        atarget_file_textField.setColumns(10);
        audio_steganography.add(atarget_file_textField);
        
        afilesize=new JLabel("");
        afilesize.setBounds(120,102,200,20);
        audio_steganography.add(afilesize);

        atarget_key_textField = new JTextField();
        atarget_key_textField.setBounds(120, 132, 200, 30);
        atarget_key_textField.setColumns(10);
        audio_steganography.add(atarget_key_textField);

        JButton audbrowse = new JButton("Browse");
        audbrowse.setBounds(380, 12, 100, 30);
        audbrowse.setFocusable(false);
        audio_steganography.add(audbrowse);
        audbrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                File image = selectImage();
                if (image == null)
                    return;
                try {
                    if(astg_set_mode.getSelectedItem() == "Embed")
                        stegoImg = new StegoImage(ImageIO.read(image), StegoImage.HIDE_MODE);
                        if(astg_set_mode.getSelectedItem() == "Extract")
                        stegoImg = new StegoImage(ImageIO.read(image), StegoImage.EXTRACT_MODE);
                        target_audio_textField.setText(image.getName());
                        astg_debug_area.append("Target Image Selected...\n");
                        audsize.setText(stegoImg.getMaxHideCapacity()/1024 + " Kilo Bytes");
                        imgFormat = getImageFormat(image);
                } catch (Exception e) {
                }
            }		
        });

        JButton afilebrowse = new JButton("Browse");
        afilebrowse.setBounds(380, 72, 100, 30);
        audio_steganography.add(afilebrowse);
        afilebrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent arg0) {
                selectFileForAudio();
            }
        });

        JButton astg_go_button = new JButton("Stegno");
        astg_go_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                String image_path = target_audio_textField.getText();
                if(!image_path.contains(".bmp") && !image_path.contains(".png") && !image_path.contains(".gif")){

                    // Check that the user has given an image path
                    astg_debug_area.append("Please make sure you have given the path to an Image...");
                    JOptionPane.showMessageDialog(null,"Please make sure you have given the path to an Image");
                    target_audio_textField.setText("");
                    throw new EmptyStackException();
                }
                Steganography steg = new Steganography();
                String current_mode = astg_set_mode.getSelectedItem();

                // picking the current mode
                if (current_mode == "Embed"){
                    hideFileIntoImage();
                    astg_debug_area.append("Done Inserting the File, into your Image...\n");
                }
                if(current_mode == "Extract"){
                    extractFileFromImage();
                    astg_debug_area.append("Done Extracting the File, from your Image...\n");
                } 
            }
        });
        astg_go_button.setBounds(298, 326, 85, 36);
        audio_steganography.add(astg_go_button);

        JButton ahelp_button = new JButton("Help");
        ahelp_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                astg_debug_area.append("Getting help Information...\n");
                String help_text = "                                           Need Some Help?\n"
                    + "1. Type out the Text you want to hide in the Image\n"
                    + "2. Select a Target image using the 'Path' Button, The Image has to be a (.jpg or .png)\n"
                    + "3. Pick your mode (Insert / Extract)\n"
                    + "4. Then simply Hit 'Crypto' and your text will be hidden or shown\n";
                    JOptionPane.showMessageDialog(null, help_text);
            }
        });
        ahelp_button.setBounds(429, 326, 70, 36);
        audio_steganography.add(ahelp_button);

        JLabel mode_lab = new JLabel("Mode");
        mode_lab.setBounds(10, 322, 150, 14);
        audio_steganography.add(mode_lab);
    }

    public static String getImageFormat(File file) {
        try {
            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
            if (!iter.hasNext()) 
                return null;
            String format = iter.next().getFormatName();
            iis.close();
            return format;
        } catch (Exception ex) {
            return null;
        }
    }

    public File selectImage() {
        JFileChooser imageChooser = new JFileChooser();
        imageChooser.setAcceptAllFileFilterUsed(false);
        imageChooser.addChoosableFileFilter(new FileNameExtensionFilter("PNG files", "png"));
        int returnVal = imageChooser.showDialog(this, "Open");
        if (returnVal == JFileChooser.APPROVE_OPTION)
            return imageChooser.getSelectedFile();
        return null;
    }

    private void selectFileForImage() {
        JFileChooser jfc = new JFileChooser();
        int returnVal = jfc.showDialog(this, "Open");
        if (returnVal != JFileChooser.APPROVE_OPTION)
            return;
        embedFile = jfc.getSelectedFile();
        itarget_file_textField.setText(embedFile.getName());
        ifilesize.setText(embedFile.length()/1024 + " Kilo Bytes");
    }

    private void hideFileIntoImage() {		
        if (stegoImg == null) {
            return;
        }
        int seedValue;
        try {
            seedValue = Integer.parseInt(itarget_key_textField.getText());
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,"Please Enter a Encryption Key!");
            return;
        }
        if (embedFile == null) {
            JOptionPane.showMessageDialog(null,"Please select the Desired File to be Hidden!");
            return;
        }
        try {
            byte[] fileBytes = Files.readAllBytes(embedFile.toPath());
            String ext = embedFile.getName().substring(embedFile.getName().lastIndexOf('.') + 1);
            byte[] extBytes = String.format("%-5s", ext).substring(0, 5).getBytes();
            byte[] bytes = new byte[fileBytes.length + extBytes.length];
            System.arraycopy(extBytes, 0, bytes, 0, extBytes.length);
            System.arraycopy(fileBytes, 0, bytes, extBytes.length, fileBytes.length);

            BufferedImage newImg = stegoImg.hide(bytes, seedValue);
            String newName = target_image_textField.getText().substring(0, target_image_textField.getText().lastIndexOf('.'));
            newName += "StegoImage." + imgFormat;

            JFileChooser jfc = new JFileChooser();
            jfc.setAcceptAllFileFilterUsed(false);
            jfc.addChoosableFileFilter(new FileNameExtensionFilter(imgFormat.toUpperCase() + " Files", imgFormat));
            jfc.setSelectedFile(new File(newName));

            int result = jfc.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION)
                return;

            ImageIO.write(newImg, imgFormat, jfc.getSelectedFile());
            JOptionPane.showMessageDialog(null,"The Embedding process is successful!");            
        } catch(IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null,"The selected file's size exceeds the Image's Hide capacity!");            
        } catch(IOException ex) {
            JOptionPane.showMessageDialog(null,"Could not save the Image File!");            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Could not Embed the text in the image!");            
        }
    }

    private void extractFileFromImage() {
        if (stegoImg == null) {
            JOptionPane.showMessageDialog(null,"Could not Embed the text in the image!");            
            return;
        }

        int seedValue;
        try {
            seedValue = Integer.parseInt(itarget_key_textField.getText());
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(null,"Please Enter a valid Key!");            
            return;
        }
        try {
            byte[] data = stegoImg.extract(seedValue);
            String format = new String(Arrays.copyOfRange(data, 0, 5)).trim();
            JFileChooser jfc = new JFileChooser();
            jfc.setSelectedFile(new File("Extracted." + format));
            int result = jfc.showSaveDialog(this);
            if (result != JFileChooser.APPROVE_OPTION)
                return;
            FileOutputStream fos = new FileOutputStream(jfc.getSelectedFile());
            fos.write(Arrays.copyOfRange(data, 5, data.length));
            fos.flush();
            fos.close();
            JOptionPane.showMessageDialog(null,"Extracted Successfully!");            
        } catch(Exception ex) {
            JOptionPane.showMessageDialog(null,"Could not Extract the hidden file from the image!");            
        }
    }
}