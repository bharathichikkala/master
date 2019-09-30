class UsermailMailer < ApplicationMailer

  def sendmail(user)

    @user =user
   attachments['sample.txt'] = File.read('E:/RUBY/writefile.txt')

   attachments['excel.xlsx'] = File.read('E:/RUBY/checklistsample.xlsx', :encoding => 'BINARY', :mode => 'rb', :content_type =>  "application/xlsx" )

   attachments['free_book.pdf'] = File.read('E:/RUBY/spring-framework-reference.pdf', :encoding => 'BINARY', :mode => 'rb', :content_type =>  "application/pdf")

   attachments['image.png'] = File.read('E:/RUBY/log.png', :encoding => 'BINARY', :mode => 'rb', :content_type =>   "image/png")

   attachments.inline['inline.png'] = File.read('E:/RUBY/log.png', :encoding => 'BINARY', :mode => 'rb', :content_type =>   "image/png")

   attachments['csv.csv'] = File.read('E:/RUBY/checklistcsv.csv', :encoding => 'BINARY', :mode => 'rb', :content_type =>  "application/csv" )


    mail(to: @user.email, subject: 'registration conformation Email')

  end
end
