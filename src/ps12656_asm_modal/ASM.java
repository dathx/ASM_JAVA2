/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ps12656_asm_modal;

import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import ps12656_asm_modal.DongHo;
import ps12656_asm_modal.Employee;

/**
 *
 * @author DMX
 */
public class ASM extends javax.swing.JFrame {

    /**
     * Creates new form ASM
     */
    ArrayList<Employee> dsnv = new ArrayList<Employee>();
    int index = -1;

    public ASM() {
        initComponents();
        setLocationRelativeTo(this);
        Employee nv = new Employee("NV01", "Hoàng Xuân Đạt", 19, "datcoi@gmail.com", 10000000);
        dsnv.add(nv);
        dsnv.add(new Employee("NV02", "Nguyễn Duy Anh", 20, "anhlun9a@gmail.com", 8000000));
        dsnv.add(new Employee("NV03", "Nguyễn Đình Việt", 22, "vietnguyen@gmail.com", 15000000));
        dsnv.add(new Employee("NV04", "Phan Văn Khoai", 19, "phankhoai@gmail.com", 17000000));
        fillToTable();

        btn_delete.setBackground(Color.pink);
        btn_exit.setBackground(Color.pink);
        btn_find.setBackground(Color.pink);
        btn_new.setBackground(Color.pink);
        btn_open.setBackground(Color.pink);
        btn_save.setBackground(Color.pink);

        btn_first.setBackground(Color.cyan);
        btn_last.setBackground(Color.cyan);
        btn_pri.setBackground(Color.cyan);
        btn_next.setBackground(Color.cyan);

        DongHo a = new DongHo(lbl_dongho);
        a.start();

    }

    public void fillToTable() {
        DefaultTableModel model = (DefaultTableModel) tbl_nhanvien.getModel();
        model.setRowCount(0);
        for (int i = 0; i < dsnv.size(); i++) {
            Employee tam = dsnv.get(i);
            Vector v = new Vector();
            v.add(tam.maNv);
            v.add(tam.hoTen);
            v.add(tam.tuoi);
            v.add(tam.email);
            v.add(tam.luong);
            model.addRow(v);
        }
    }

    public void deleteEmployee() {
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng dữ liệu cần xóa");
        } else {
            dsnv.remove(index);
            fillToTable();
            index = -1;
            JOptionPane.showMessageDialog(this, "Đã xóa thành công");

        }
    }

    public void clearForm() {
        txt_ten.setText("");
        txt_ma.setText("");
        txt_tuoi.setText("");
        txt_email.setText("");
        txt_luong.setText("");
        txt_tuoi.setBackground(Color.white);
        txt_email.setBackground(Color.white);
        txt_luong.setBackground(Color.white);
        txt_ma.setBackground(Color.white);
    }

    public void fillControl(int index) {
        Employee tam = dsnv.get(index);
        txt_ma.setText(tam.maNv);
        txt_ten.setText(tam.hoTen);
        txt_tuoi.setText(tam.tuoi+"");
        txt_email.setText(tam.email);
        txt_luong.setText(tam.luong+"");
    }

    public void showDetai() {
        index = tbl_nhanvien.getSelectedRow();
        Employee tam = dsnv.get(index);
        txt_ma.setText(tam.maNv);
        txt_ten.setText(tam.hoTen);
        txt_tuoi.setText(tam.tuoi + "");
        txt_email.setText(tam.email);
        txt_luong.setText(tam.luong + "");
    }

    public void addEmployee() {
        boolean kt = true;
        try {

            String ma = txt_ma.getText();
            String ten = txt_ten.getText();
            int tuoi = Integer.parseInt(txt_tuoi.getText());
            String email = txt_email.getText();
            double luong = Double.parseDouble(txt_luong.getText());
            // check manv
            for (Employee i : dsnv) {
                if (i.getMaNv().equalsIgnoreCase(ma)) {
                    kt = false;
                    JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại");
                    txt_ma.setBackground(Color.yellow);
                    break;
                }
            }

            // check tuoi
            try {
                if (tuoi < 16 || tuoi > 55) {
                    throw new Exception();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Tuổi phải từ 16 đến 55");
                txt_tuoi.setBackground(Color.yellow);
                kt = false;
            }
            // CHECK EMAIL
            try {
                Employee.testEmail(txt_email.getText());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Email không hợp lệ !");
                txt_email.setBackground(Color.yellow);
                kt = false;
            }
            //CHECK LUONG
            try {
                if (luong < 5000000) {
                    throw new Exception();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Lương phải lớn hơn 5.000.000");
                kt = false;
                txt_luong.setBackground(Color.yellow);
            }
            if (kt == true) {
                Employee nv = new Employee(ma, ten, tuoi, email, luong);
                dsnv.add(nv);
                fillToTable();
                clearForm();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chưa nhập dữ liệu hoặc dữ liệu không hợp lệ");
        }

    }

    public void updateEmployee() {
        String ma = txt_ma.getText();
        String ten = txt_ten.getText();
        int tuoi = Integer.parseInt(txt_tuoi.getText());
        String email = txt_email.getText();
        double luong = Double.parseDouble(txt_luong.getText());
        Employee nv = new Employee(ma, ten, tuoi, email, luong);
        dsnv.set(index, nv);
        JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
        index = -1;
        fillToTable();
        clearForm();
    }

    public void findEmployee() {
        String nvtam = JOptionPane.showInputDialog("Mời bạn nhập mã nhân viên cần tìm !");
        boolean kq = false;
        for (Employee i : dsnv) {
            if (i.getMaNv().equalsIgnoreCase(nvtam)) {
                index = dsnv.indexOf(i);
                tbl_nhanvien.setRowSelectionInterval(index, index);
                kq = true;
                break;
            }
            if (nvtam.equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã nhân viên cần tìm !");
                kq = true;
                break;
            }
        }
        if (kq == false) {
            JOptionPane.showMessageDialog(this, "Không có nhân viên có mã: " + nvtam);
        }
    }

   
    public void saveFile() {
        JFileChooser chooser = new JFileChooser();
        int x = chooser.showSaveDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            String duongdan = chooser.getSelectedFile().getAbsolutePath().toString();
            System.out.println(duongdan);
            try {
                FileOutputStream out = new FileOutputStream(duongdan);
                ObjectOutputStream objout = new ObjectOutputStream(out);
                objout.writeObject(dsnv);

                out.close();
                objout.close();

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public void openFile() throws IOException {
        JFileChooser select = new JFileChooser();
        int x = select.showOpenDialog(null);
        if (x == JFileChooser.APPROVE_OPTION) {
            try {
                String file = select.getSelectedFile().getAbsolutePath().toString();
                FileInputStream in = new FileInputStream(file);
                ObjectInputStream obj = new ObjectInputStream(in);
                dsnv = (ArrayList<Employee>) obj.readObject();
                fillToTable();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
    }

    public void record() {
        lbl_record.setText("Record: " + (index + 1) + " of " + dsnv.size());
    }
    
    public  void XapXepTuoi(){
        Comparator<Employee> comp = new Comparator<Employee>() {
            @Override
            public int compare(Employee o1, Employee o2) {
                return o1.tuoi-o2.tuoi;
            }
        };
        Collections.sort(dsnv,comp);
    }
 
        
    public  void XapXepLuong(){
      Comparator<Employee> Com = new Comparator<Employee>() {
          @Override
          public int compare(Employee o1, Employee o2) {
             double l1 = o1.luong;
             double l2 = o2.luong;
            return  (int) (l1 - l2);
          }
      };
      Collections.sort(dsnv,Com);
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txt_email = new javax.swing.JTextField();
        btn_next = new javax.swing.JButton();
        txt_luong = new javax.swing.JTextField();
        btn_last = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        btn_new = new javax.swing.JButton();
        btn_save = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_find = new javax.swing.JButton();
        btn_open = new javax.swing.JButton();
        btn_exit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_nhanvien = new javax.swing.JTable();
        lbl_dongho = new javax.swing.JLabel();
        label1 = new java.awt.Label();
        lbl_record = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_ma = new javax.swing.JTextField();
        txt_ten = new javax.swing.JTextField();
        btn_first = new javax.swing.JButton();
        txt_tuoi = new javax.swing.JTextField();
        btn_pri = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("                                                   Quản Lý  Nhân Viên");
        setBackground(new java.awt.Color(153, 255, 255));

        txt_email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_emailActionPerformed(evt);
            }
        });

        btn_next.setText(">>");
        btn_next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nextActionPerformed(evt);
            }
        });

        btn_last.setText(">|");
        btn_last.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_lastActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_new.setText("New");
        btn_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_newActionPerformed(evt);
            }
        });

        btn_save.setText("Save");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_delete.setText("Delete");
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });

        btn_find.setText("Find");
        btn_find.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_findActionPerformed(evt);
            }
        });

        btn_open.setText("Open");
        btn_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_openActionPerformed(evt);
            }
        });

        btn_exit.setText("Exit");
        btn_exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_exitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_delete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_new, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_save, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_find, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_open, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_new)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_save)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_find)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_open)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_exit)
                .addGap(21, 21, 21))
        );

        tbl_nhanvien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ", "HỌ VÀ TÊN", "TUỔI", "EMAIL", "LƯƠNG"
            }
        ));
        tbl_nhanvien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_nhanvienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_nhanvien);

        lbl_dongho.setForeground(new java.awt.Color(204, 0, 0));
        lbl_dongho.setText("Đồng Hồ");
        lbl_dongho.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                lbl_donghoAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        label1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        label1.setText("QUẢN LÝ NHÂN VIÊN");

        lbl_record.setForeground(new java.awt.Color(204, 0, 0));
        lbl_record.setText("Record");

        jLabel1.setText("MÃ NHÂN VIÊN");

        jLabel2.setText("HỌ VÀ TÊN");

        jLabel3.setText("TUỔI");

        jLabel4.setText("EMAIL");

        jLabel5.setText("LƯƠNG");

        txt_ma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_maActionPerformed(evt);
            }
        });

        btn_first.setText("|<");
        btn_first.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_firstActionPerformed(evt);
            }
        });

        btn_pri.setText("<<");
        btn_pri.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_priActionPerformed(evt);
            }
        });

        jButton1.setText("Xắp xếp theo tuổi");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Xắp xếp theo lương");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btn_first, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_pri)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_next)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btn_last)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbl_record, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_ten)
                                        .addComponent(txt_tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txt_email, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                                    .addComponent(txt_luong, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lbl_dongho, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))))
                .addGap(68, 68, 68))
            .addGroup(layout.createSequentialGroup()
                .addGap(229, 229, 229)
                .addComponent(jButton1)
                .addGap(33, 33, 33)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_dongho, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_ma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_ten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_luong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_first)
                            .addComponent(btn_pri)
                            .addComponent(btn_next)
                            .addComponent(btn_last)
                            .addComponent(lbl_record)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(25, 25, 25))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nextActionPerformed
        // TODO add your handling code here:
        if (dsnv.size() != 0 && index != -1) {
            if (index < dsnv.size() - 1) {
                index++;
                tbl_nhanvien.setRowSelectionInterval(index, index);
                fillControl(index);
                record();
            }
        }
    }//GEN-LAST:event_btn_nextActionPerformed

    private void btn_lastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_lastActionPerformed
        // TODO add your handling code here:
        if (dsnv.size() > 0) {
            index = dsnv.size() - 1;
            tbl_nhanvien.setRowSelectionInterval(index, index);
            fillControl(index);
            record();
        }
    }//GEN-LAST:event_btn_lastActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        clearForm();
        index = -1;
        tbl_nhanvien.clearSelection();
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        if (index == -1) {
            addEmployee();
        } else {
            updateEmployee();
        }
    }//GEN-LAST:event_btn_saveActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        this.deleteEmployee();
        this.clearForm();
        index = -1;

    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_openActionPerformed
        try {
            // TODO add your handling code here
            this.openFile();
        } catch (IOException ex) {
            System.out.println(ex.toString());
        }


    }//GEN-LAST:event_btn_openActionPerformed

    private void btn_exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_exitActionPerformed
        // TODO add your handling code here:
        this.saveFile();
        JOptionPane.showMessageDialog(this, "Chào Tạm Biệt !");
        System.exit(0);
    }//GEN-LAST:event_btn_exitActionPerformed

    private void tbl_nhanvienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_nhanvienMouseClicked
        // TODO add your handling code here:
        this.showDetai();
        this.record();
    }//GEN-LAST:event_tbl_nhanvienMouseClicked

    private void txt_maActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_maActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_maActionPerformed

    private void btn_firstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_firstActionPerformed
        // TODO add your handling code here:
        if (dsnv.size() > 0) {
            index = 0;
            tbl_nhanvien.setRowSelectionInterval(index, index);
            fillControl(index);
            record();
        }
    }//GEN-LAST:event_btn_firstActionPerformed

    private void btn_priActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_priActionPerformed
        // TODO add your handling code here:
        if (dsnv.size() != 0 && index != -11) {
            if (index > 0) {
                index--;
                tbl_nhanvien.setRowSelectionInterval(index, index);
                fillControl(index);
                record();
            }
        }
    }//GEN-LAST:event_btn_priActionPerformed

    private void btn_findActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_findActionPerformed
        // TODO add your handling code here:
        this.findEmployee();
        this.showDetai();
        this.record();

    }//GEN-LAST:event_btn_findActionPerformed

    private void txt_emailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_emailActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_emailActionPerformed

    private void lbl_donghoAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_lbl_donghoAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_lbl_donghoAncestorAdded

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        this.XapXepTuoi();
        this.fillToTable();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.XapXepLuong();
        this.fillToTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ASM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ASM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ASM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ASM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ASM().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_exit;
    private javax.swing.JButton btn_find;
    private javax.swing.JButton btn_first;
    private javax.swing.JButton btn_last;
    private javax.swing.JButton btn_new;
    private javax.swing.JButton btn_next;
    private javax.swing.JButton btn_open;
    private javax.swing.JButton btn_pri;
    private javax.swing.JButton btn_save;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Label label1;
    private javax.swing.JLabel lbl_dongho;
    private javax.swing.JLabel lbl_record;
    private javax.swing.JTable tbl_nhanvien;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_luong;
    private javax.swing.JTextField txt_ma;
    private javax.swing.JTextField txt_ten;
    private javax.swing.JTextField txt_tuoi;
    // End of variables declaration//GEN-END:variables

}
