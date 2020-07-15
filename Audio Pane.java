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

        JLabel amode_lab = new JLabel("Mode");
        amode_lab.setBounds(10, 322, 150, 14);
        audio_steganography.add(amode_lab);