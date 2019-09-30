class MailController < ApplicationController

  # require 'rufus-scheduler'
  #
  # scheduler = Rufus::Scheduler.new
  #
  # scheduler.every '5m' do
  def readmail

    require 'gmail'

    gmail = Gmail.connect('bchikkala@metanoiasolutions.net', 'chikkalamss')

   #count unread mails
    count =gmail.inbox.count(:unread)
    puts count


      @email = Array.new

       gmail.inbox.find(:unread).each do |email|
     #  email.read!
        #  puts email.text_part ? email.text_part.body.decoded : nil
         reademail =email.text_part ? email.text_part.body.decoded : nil
        @email.push(reademail)
        #  puts email.body


        #attachments
        folder = 'E:\RUBY\download'
        email.message.attachments.each do |f|
        File.write(File.join(folder, f.filename),f.body.decoded, :encoding => 'BINARY', :mode => 'wb')
        end
       #email.delete!
       end

       gmail.logout

  end
end

# end
